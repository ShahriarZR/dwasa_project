package com.example.DWASA_Backend.user;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserResponse createUser(CreateUserRequest request) {
		Long adminId = resolveAuthenticatedAdminId();

		String empId = request.getEmpId().trim();
		if (userRepository.findByEmpId(empId).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "EMP_ID already exists");
		}
		if (request.getEmail() != null && !request.getEmail().isBlank() && userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL already exists");
		}

		User user = new User();
		user.setName(request.getName());
		user.setDepartment(request.getDepartment());
		user.setDesignation(request.getDesignation());
		user.setCreatedBy(adminId);
		user.setEmpId(empId);
		user.setMobileNo(request.getMobileNo());
		user.setEmail(request.getEmail());
		user.setJoinDate(request.getJoinDate());
		user.setBirthDate(request.getBirthDate());
		user.setReligion(request.getReligion());
		user.setGender(request.getGender());
		user.setAddress(request.getAddress());
		user.setRemarks(request.getRemarks());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		User saved = userRepository.save(user);
		saved.setRootId(saved.getId());
		saved = userRepository.save(saved);
		return new UserResponse(saved);
	}

	@Transactional(readOnly = true)
	public MyProfileResponse getMyProfile() {
		String empId = resolveAuthenticatedEmpId();
		User user = userRepository.findByEmpId(empId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
		return new MyProfileResponse(user);
	}

	@Transactional
	public MyProfileResponse updateMyProfile(UpdateProfileRequest request) {
		User user = resolveAuthenticatedUser();

		if (request.getName() != null) {
			user.setName(request.getName().trim());
		}
		if (request.getDesignation() != null) {
			user.setDesignation(request.getDesignation().trim());
		}
		if (request.getMobileNo() != null) {
			user.setMobileNo(request.getMobileNo().trim());
		}
		if (request.getJoinDate() != null) {
			user.setJoinDate(request.getJoinDate());
		}
		if (request.getBirthDate() != null) {
			user.setBirthDate(request.getBirthDate());
		}
		if (request.getReligion() != null) {
			user.setReligion(request.getReligion().trim());
		}
		if (request.getGender() != null) {
			user.setGender(request.getGender());
		}
		if (request.getAddress() != null) {
			user.setAddress(request.getAddress().trim());
		}
		if (request.getRemarks() != null) {
			user.setRemarks(request.getRemarks().trim());
		}

		if (request.getEmail() != null) {
			String updatedEmail = request.getEmail().trim();
			if (updatedEmail.isBlank()) {
				user.setEmail(null);
			} else {
				userRepository.findByEmail(updatedEmail)
						.filter(existingUser -> !existingUser.getId().equals(user.getId()))
						.ifPresent(existingUser -> {
							throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL already exists");
						});
				user.setEmail(updatedEmail);
			}
		}

		user.setUpdatedBy(user.getId());
		User savedUser = userRepository.save(user);
		return new MyProfileResponse(savedUser);
	}

	@Transactional
	public void updateMyPassword(UpdatePasswordRequest request) {
		User user = resolveAuthenticatedUser();

		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"New password and confirm password do not match");
		}

		if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
		}

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		user.setUpdatedBy(user.getId());
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
				.map(UserResponse::new)
				.toList();
	}

	@Transactional
	public UserResponse updateUser(Long userId, AdminUpdateUserRequest request) {
		Long updaterId = resolveAuthenticatedAdminId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		if (request.getName() != null) {
			user.setName(request.getName().trim());
		}
		if (request.getDesignation() != null) {
			user.setDesignation(request.getDesignation().trim());
		}
		if (request.getMobileNo() != null) {
			user.setMobileNo(request.getMobileNo().trim());
		}
		if (request.getJoinDate() != null) {
			user.setJoinDate(request.getJoinDate());
		}
		if (request.getBirthDate() != null) {
			user.setBirthDate(request.getBirthDate());
		}
		if (request.getReligion() != null) {
			user.setReligion(request.getReligion().trim());
		}
		if (request.getGender() != null) {
			user.setGender(request.getGender());
		}
		if (request.getAddress() != null) {
			user.setAddress(request.getAddress().trim());
		}
		if (request.getRemarks() != null) {
			user.setRemarks(request.getRemarks().trim());
		}

		if (request.getEmpId() != null) {
			String updatedEmpId = request.getEmpId().trim();
			if (updatedEmpId.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMP_ID cannot be blank");
			}
			userRepository.findByEmpId(updatedEmpId)
					.filter(existingUser -> !existingUser.getId().equals(user.getId()))
					.ifPresent(existingUser -> {
						throw new ResponseStatusException(HttpStatus.CONFLICT, "EMP_ID already exists");
					});
			user.setEmpId(updatedEmpId);
		}

		if (request.getEmail() != null) {
			String updatedEmail = request.getEmail().trim();
			if (updatedEmail.isBlank()) {
				user.setEmail(null);
			} else {
				userRepository.findByEmail(updatedEmail)
						.filter(existingUser -> !existingUser.getId().equals(user.getId()))
						.ifPresent(existingUser -> {
							throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL already exists");
						});
				user.setEmail(updatedEmail);
			}
		}

		if (request.getDepartment() != null) {
			user.setDepartment(request.getDepartment());
		}

		if (request.getPassword() != null) {
			String updatedPassword = request.getPassword().trim();
			if (updatedPassword.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be blank");
			}
			user.setPassword(passwordEncoder.encode(updatedPassword));
		}

		user.setRootId(user.getId());
		user.setUpdatedBy(updaterId);

		User savedUser = userRepository.save(user);
		return new UserResponse(savedUser);
	}

	@Transactional
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		userRepository.delete(user);
	}

	private String resolveAuthenticatedEmpId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authentication");
		}
		return String.valueOf(authentication.getPrincipal());
	}

	private Long resolveAuthenticatedAdminId() {
		String adminEmpId = resolveAuthenticatedEmpId();
		return userRepository.findByEmpId(adminEmpId)
				.map(User::getId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin user not found"));
	}

	private User resolveAuthenticatedUser() {
		String empId = resolveAuthenticatedEmpId();
		return userRepository.findByEmpId(empId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
	}
}
