package com.example.DWASA_Backend.product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ProductResponse {
	private final Long id;
	private final String productCode;
	private final String name;
	private final BigDecimal capacityMl;
	private final Integer unit;
	private final String remarks;
	private final ProductStatus status;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public ProductResponse(ProductType product) {
		this.id = product.getId();
		this.productCode = product.getProductCode();
		this.name = product.getName();
		this.capacityMl = product.getCapacityMl();
		this.unit = product.getUnit();
		this.remarks = product.getRemarks();
		this.status = product.getStatus();
		this.createdAt = product.getCreatedAt();
		this.createdBy = product.getCreatedBy();
		this.updatedAt = product.getUpdatedAt();
		this.updatedBy = product.getUpdatedBy();
		this.deletedAt = product.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getCapacityMl() {
		return capacityMl;
	}

	public Integer getUnit() {
		return unit;
	}

	public String getRemarks() {
		return remarks;
	}

	public ProductStatus getStatus() {
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