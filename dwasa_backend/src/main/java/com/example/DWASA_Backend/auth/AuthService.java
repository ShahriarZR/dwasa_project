package com.example.DWASA_Backend.auth;

import com.example.DWASA_Backend.security.JwtService;
import com.example.DWASA_Backend.user.User;
import com.example.DWASA_Backend.user.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public LoginResponse login(String empId, String rawPassword) {
		User user = userRepository.findByEmpId(empId)
				.orElseThrow(() -> new InvalidCredentialsException());

		String storedPassword = Optional.ofNullable(user.getPassword()).orElse("");
		boolean matches = passwordMatches(rawPassword, storedPassword);
		if (!matches) {
			throw new InvalidCredentialsException();
		}

		// If legacy plaintext passwords exist, upgrade to BCrypt on successful login
		if (!looksLikeBcrypt(storedPassword)) {
			user.setPassword(passwordEncoder.encode(rawPassword));
			user.setUpdatedBy(user.getId());
			userRepository.save(user);
		}

		String dept = user.getDepartment() == null ? null : user.getDepartment().name();
		String token = jwtService.generateToken(user.getEmpId(), dept);
		return new LoginResponse(token, jwtService.getExpirationSeconds(), user.getEmpId(), user.getName(), dept);
	}

	private boolean passwordMatches(String rawPassword, String storedPassword) {
		if (looksLikeBcrypt(storedPassword)) {
			return passwordEncoder.matches(rawPassword, storedPassword);
		}
		return storedPassword.equals(rawPassword);
	}

	private boolean looksLikeBcrypt(String storedPassword) {
		return storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$");
	}
}
