package com.example.DWASA_Backend.machine;

import java.time.OffsetDateTime;

public class MachineResponse {
	private final Long id;
	private final String machineName;
	private final String section;
	private final String location;
	private final String brand;
	private final String remarks;
	private final MachineStatus status;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public MachineResponse(Machine machine) {
		this.id = machine.getId();
		this.machineName = machine.getMachineName();
		this.section = machine.getSection();
		this.location = machine.getLocation();
		this.brand = machine.getBrand();
		this.remarks = machine.getRemarks();
		this.status = machine.getStatus();
		this.createdAt = machine.getCreatedAt();
		this.createdBy = machine.getCreatedBy();
		this.updatedAt = machine.getUpdatedAt();
		this.updatedBy = machine.getUpdatedBy();
		this.deletedAt = machine.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public String getMachineName() {
		return machineName;
	}

	public String getSection() {
		return section;
	}

	public String getLocation() {
		return location;
	}

	public String getBrand() {
		return brand;
	}

	public String getRemarks() {
		return remarks;
	}

	public MachineStatus getStatus() {
		return status;
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
