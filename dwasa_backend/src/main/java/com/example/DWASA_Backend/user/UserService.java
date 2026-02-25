package com.example.DWASA_Backend.user;

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
		if (request.getDepartment() != Department.ADMIN) {
			user.setRootId(adminId);
		}
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
		if (request.getDepartment() == Department.ADMIN) {
			saved.setRootId(saved.getId());
			saved = userRepository.save(saved);
		}
		return new UserResponse(saved);
	}

	@Transactional(readOnly = true)
	public UserResponse getMyProfile() {
		String empId = resolveAuthenticatedEmpId();
		User user = userRepository.findByEmpId(empId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
		return new UserResponse(user);
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
}
