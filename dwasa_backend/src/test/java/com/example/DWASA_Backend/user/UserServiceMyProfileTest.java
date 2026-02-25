package com.example.DWASA_Backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	void getMyProfile_returnsUserResponse_forAuthenticatedUser() {
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
		UserResponse response = service.getMyProfile();

		assertEquals(7L, response.getId());
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
}
