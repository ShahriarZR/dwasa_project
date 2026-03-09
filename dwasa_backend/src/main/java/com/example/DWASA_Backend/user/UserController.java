package com.example.DWASA_Backend.user;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
		return userService.createUser(request);
	}

	@GetMapping
	public List<UserResponse> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping(path = "/me")
	public MyProfileResponse getMyProfile() {
		return userService.getMyProfile();
	}

	@PutMapping(path = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
	public MyProfileResponse updateMyProfile(@Valid @RequestBody UpdateProfileRequest request) {
		return userService.updateMyProfile(request);
	}

	@PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserResponse updateUser(
			@PathVariable Long userId,
			@Valid @RequestBody AdminUpdateUserRequest request) {
		return userService.updateUser(userId, request);
	}

	@PutMapping(path = "/me/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMyPassword(@Valid @RequestBody UpdatePasswordRequest request) {
		userService.updateMyPassword(request);
	}

	@DeleteMapping(path = "/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
	}
}
