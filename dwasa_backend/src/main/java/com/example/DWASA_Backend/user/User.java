package com.example.DWASA_Backend.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "DWASA_USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "NAME", nullable = false, length = 150)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "DEPARTMENT", nullable = false, length = 50)
	private Department department;

	@Column(name = "DESIGNATION", length = 100)
	private String designation;

	@Column(name = "ROOT_ID")
	private Long rootId;

	@NotBlank
	@Column(name = "EMP_ID", nullable = false, length = 50, unique = true)
	private String empId;

	@Column(name = "MOBILE_NO", length = 20)
	private String mobileNo;

	@Email
	@Column(name = "EMAIL", length = 150, unique = true)
	private String email;

	@Column(name = "JOIN_DATE")
	private LocalDate joinDate;

	@Column(name = "BIRTH_DATE")
	private LocalDate birthDate;

	@Column(name = "RELIGION", length = 50)
	private String religion;

	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", length = 20)
	private Gender gender;

	@Lob
	@Column(name = "ADDRESS")
	private String address;

	@Lob
	@Column(name = "REMARKS")
	private String remarks;

	@NotBlank
	@Column(name = "PASSWORD", nullable = false, length = 255)
	private String password;

	@CreationTimestamp
	@Column(name = "CREATED_AT", nullable = false)
	private java.time.OffsetDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT", nullable = false)
	private java.time.OffsetDateTime updatedAt;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.time.OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public java.time.OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
}
