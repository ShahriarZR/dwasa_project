package com.example.DWASA_Backend.ingradient;

import java.time.OffsetDateTime;

public class IngradientResponse {
	private final Long id;
	private final String name;
	private final String remarks;
	private final String fullName;
	private final Integer areaOfUse;
	private final Long createdBy;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public IngradientResponse(Ingradient ingradient) {
		this.id = ingradient.getId();
		this.name = ingradient.getName();
		this.remarks = ingradient.getRemarks();
		this.fullName = ingradient.getFullName();
		this.areaOfUse = ingradient.getAreaOfUse();
		this.createdBy = ingradient.getCreatedBy();
		this.updatedBy = ingradient.getUpdatedBy();
		this.deletedAt = ingradient.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getFullName() {
		return fullName;
	}

	public Integer getAreaOfUse() {
		return areaOfUse;
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