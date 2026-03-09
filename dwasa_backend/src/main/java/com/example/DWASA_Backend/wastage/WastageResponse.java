package com.example.DWASA_Backend.wastage;

import java.time.OffsetDateTime;

public class WastageResponse {
	private final Long id;
	private final Long productId;
	private final Long ingradientId;
	private final Long sectionId;
	private final Integer unit;
	private final String remarks;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public WastageResponse(Wastage wastage) {
		this.id = wastage.getId();
		this.productId = wastage.getProductId();
		this.ingradientId = wastage.getIngradientId();
		this.sectionId = wastage.getSectionId();
		this.unit = wastage.getUnit();
		this.remarks = wastage.getRemarks();
		this.createdAt = wastage.getCreatedAt();
		this.createdBy = wastage.getCreatedBy();
		this.updatedAt = wastage.getUpdatedAt();
		this.updatedBy = wastage.getUpdatedBy();
		this.deletedAt = wastage.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public Long getProductId() {
		return productId;
	}

	public Long getIngradientId() {
		return ingradientId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public Integer getUnit() {
		return unit;
	}

	public String getRemarks() {
		return remarks;
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