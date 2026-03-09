package com.example.DWASA_Backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class UserServiceMyProfileTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@AfterEach
	void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void getMyProfile_returnsMyProfileResponse_forAuthenticatedUser() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("E-100", null)
		);

		User user = new User();
		user.setId(7L);
		user.setEmpId("E-100");
		user.setName("Alice");
		user.setDepartment(Department.ADMIN);

		when(userRepository.findByEmpId("E-100")).thenReturn(Optional.of(user));

		UserService service = new UserService(userRepository, passwordEncoder);
		MyProfileResponse response = service.getMyProfile();

		assertEquals("E-100", response.getEmpId());
		assertEquals("Alice", response.getName());
		assertEquals(Department.ADMIN, response.getDepartment());
	}

	@Test
	void getMyProfile_throws401_whenUnauthenticated() {
		SecurityContextHolder.clearContext();

		UserService service = new UserService(userRepository, passwordEncoder);
		ResponseStatusException ex = assertThrows(ResponseStatusException.class, service::getMyProfile);
		assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
	}

	@Test
	void updateMyProfile_updatesEmailAndMobile() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("E-100", null)
		);

		User user = new User();
		user.setId(7L);
		user.setEmpId("E-100");
		user.setEmail("old@example.com");
		user.setMobileNo("01700000000");

		when(userRepository.findByEmpId("E-100")).thenReturn(Optional.of(user));
		when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		UpdateProfileRequest request = new UpdateProfileRequest();
		request.setEmail("new@example.com");
		request.setMobileNo("01811111111");

		UserService service = new UserService(userRepository, passwordEncoder);
		MyProfileResponse response = service.updateMyProfile(request);

		assertEquals("new@example.com", response.getEmail());
		assertEquals("01811111111", response.getMobileNo());
	}

	@Test
	void updateMyPassword_updatesEncodedPassword_whenOldPasswordMatches() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("E-100", null)
		);

		User user = new User();
		user.setEmpId("E-100");
		user.setPassword("encoded-old");

		when(userRepository.findByEmpId("E-100")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("old-pass", "encoded-old")).thenReturn(true);
		when(passwordEncoder.encode("new-pass")).thenReturn("encoded-new");

		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setOldPassword("old-pass");
		request.setNewPassword("new-pass");
		request.setConfirmPassword("new-pass");

		UserService service = new UserService(userRepository, passwordEncoder);
		service.updateMyPassword(request);

		assertEquals("encoded-new", user.getPassword());
		verify(userRepository).save(user);
	}

	@Test
	void updateMyPassword_throws400_whenConfirmPasswordMismatch() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("E-100", null)
		);

		User user = new User();
		user.setEmpId("E-100");

		when(userRepository.findByEmpId("E-100")).thenReturn(Optional.of(user));

		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setOldPassword("old-pass");
		request.setNewPassword("new-pass");
		request.setConfirmPassword("different-pass");

		UserService service = new UserService(userRepository, passwordEncoder);
		ResponseStatusException ex = assertThrows(ResponseStatusException.class,
				() -> service.updateMyPassword(request));

		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
		verify(passwordEncoder, never()).matches(anyString(), anyString());
	}
}
