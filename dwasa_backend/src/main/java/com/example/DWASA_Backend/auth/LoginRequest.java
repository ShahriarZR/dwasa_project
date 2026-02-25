package com.example.DWASA_Backend.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String empId;

	@NotBlank
	private String password;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
