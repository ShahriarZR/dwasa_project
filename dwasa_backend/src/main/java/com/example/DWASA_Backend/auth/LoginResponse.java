package com.example.DWASA_Backend.auth;

public class LoginResponse {
	private final String token;
	private final String tokenType;
	private final long expiresInSeconds;
	private final String empId;
	private final String name;
	private final String department;

	public LoginResponse(String token, long expiresInSeconds, String empId, String name, String department) {
		this.token = token;
		this.tokenType = "Bearer";
		this.expiresInSeconds = expiresInSeconds;
		this.empId = empId;
		this.name = name;
		this.department = department;
	}

	public String getToken() {
		return token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public long getExpiresInSeconds() {
		return expiresInSeconds;
	}

	public String getEmpId() {
		return empId;
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}
}
