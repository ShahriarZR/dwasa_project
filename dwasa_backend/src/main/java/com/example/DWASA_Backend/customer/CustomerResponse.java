package com.example.DWASA_Backend.customer;

import java.time.OffsetDateTime;

public class CustomerResponse {
	private final Long id;
	private final String name;
	private final String email;
	private final String phone;
	private final CustomerType type;
	private final String contactPerson;
	private final String remarks;
	private final CustomerStatus status;
	private final String address;
	private final String shopLocation;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public CustomerResponse(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.email = customer.getEmail();
		this.phone = customer.getPhone();
		this.type = customer.getType();
		this.contactPerson = customer.getContactPerson();
		this.remarks = customer.getRemarks();
		this.status = customer.getStatus();
		this.address = customer.getAddress();
		this.shopLocation = customer.getShopLocation();
		this.createdAt = customer.getCreatedAt();
		this.createdBy = customer.getCreatedBy();
		this.updatedAt = customer.getUpdatedAt();
		this.updatedBy = customer.getUpdatedBy();
		this.deletedAt = customer.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public CustomerType getType() {
		return type;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public String getRemarks() {
		return remarks;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public String getAddress() {
		return address;
	}

	public String getShopLocation() {
		return shopLocation;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public OffsetDateTime getDeletedAt() {
		return deletedAt;
	}
}
