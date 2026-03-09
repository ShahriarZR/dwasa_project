package com.example.DWASA_Backend.unit;

import java.time.OffsetDateTime;

public class UnitResponse {
	private final Long id;
	private final String name;
	private final Integer status;
	private final String remarks;
	private final Long createdBy;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public UnitResponse(Unit unit) {
		this.id = unit.getId();
		this.name = unit.getName();
		this.status = unit.getStatus();
		this.remarks = unit.getRemarks();
		this.createdBy = unit.getCreatedBy();
		this.updatedBy = unit.getUpdatedBy();
		this.deletedAt = unit.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getStatus() {
		return status;
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

	public OffsetDateTime getDeletedAt() {
		return deletedAt;
	}
}