package com.example.DWASA_Backend.user;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class UserResponse {
	private final Long id;
	private final String name;
	private final Department department;
	private final String designation;
	private final Long rootId;
	private final String empId;
	private final String mobileNo;
	private final String email;
	private final LocalDate joinDate;
	private final LocalDate birthDate;
	private final String religion;
	private final Gender gender;
	private final String address;
	private final String remarks;
	private final Long createdBy;
	private final Long updatedBy;
	private final OffsetDateTime createdAt;
	private final OffsetDateTime updatedAt;

	public UserResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.department = user.getDepartment();
		this.designation = user.getDesignation();
		this.rootId = user.getRootId();
		this.empId = user.getEmpId();
		this.mobileNo = user.getMobileNo();
		this.email = user.getEmail();
		this.joinDate = user.getJoinDate();
		this.birthDate = user.getBirthDate();
		this.religion = user.getReligion();
		this.gender = user.getGender();
		this.address = user.getAddress();
		this.remarks = user.getRemarks();
		this.createdBy = user.getCreatedBy();
		this.updatedBy = user.getUpdatedBy();
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Department getDepartment() {
		return department;
	}

	public String getDesignation() {
		return designation;
	}

	public Long getRootId() {
		return rootId;
	}

	public String getEmpId() {
		return empId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getReligion() {
		return religion;
	}

	public Gender getGender() {
		return gender;
	}

	public String getAddress() {
		return address;
	}

	public String getRemarks() {
		return remarks;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}
}
