package com.example.DWASA_Backend.sales;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class SalesResponse {
	private final Long id;
	private final Long productId;
	private final BigDecimal ratePerPcs;
	private final BigDecimal ratePerCase;
	private final Integer minimumQuantity;
	private final BigDecimal productionCost;
	private final Integer productionCostBasis;
	private final BigDecimal vat;
	private final BigDecimal sd;
	private final Integer appliesIn;
	private final Integer status;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;

	public SalesResponse(Sales sales) {
		this.id = sales.getId();
		this.productId = sales.getProductId();
		this.ratePerPcs = sales.getRatePerPcs();
		this.ratePerCase = sales.getRatePerCase();
		this.minimumQuantity = sales.getMinimumQuantity();
		this.productionCost = sales.getProductionCost();
		this.productionCostBasis = sales.getProductionCostBasis();
		this.vat = sales.getVat();
		this.sd = sales.getSd();
		this.appliesIn = sales.getAppliesIn();
		this.status = sales.getStatus();
		this.createdAt = sales.getCreatedAt();
		this.createdBy = sales.getCreatedBy();
		this.updatedAt = sales.getUpdatedAt();
		this.updatedBy = sales.getUpdatedBy();
		this.deletedAt = sales.getDeletedAt();
	}

	public Long getId() {
		return id;
	}

	public Long getProductId() {
		return productId;
	}

	public BigDecimal getRatePerPcs() {
		return ratePerPcs;
	}

	public BigDecimal getRatePerCase() {
		return ratePerCase;
	}

	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	public BigDecimal getProductionCost() {
		return productionCost;
	}

	public Integer getProductionCostBasis() {
		return productionCostBasis;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public BigDecimal getSd() {
		return sd;
	}

	public Integer getAppliesIn() {
		return appliesIn;
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
