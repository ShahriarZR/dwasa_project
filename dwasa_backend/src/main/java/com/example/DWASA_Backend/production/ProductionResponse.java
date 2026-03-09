package com.example.DWASA_Backend.production;

import java.time.OffsetDateTime;

public class ProductionResponse {
	private final Long id;
	private final Long productId;
	private final Integer wrappingQuantity;
	private final Integer status;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public ProductionResponse(Production production) {
		this.id = production.getId();
		this.productId = production.getProductId();
		this.wrappingQuantity = production.getWrappingQuantity();
		this.status = production.getStatus();
		this.createdAt = production.getCreatedAt();
		this.createdBy = production.getCreatedBy();
		this.updatedAt = production.getUpdatedAt();
		this.updatedBy = production.getUpdatedBy();
		this.deletedAt = production.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public Long getProductId() {
		return productId;
	}

	public Integer getWrappingQuantity() {
		return wrappingQuantity;
	}

	public Integer getStatus() {
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
