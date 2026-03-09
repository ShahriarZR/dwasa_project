package com.example.DWASA_Backend.sales;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "SALES")
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "PRODUCT_ID", nullable = false)
	private Long productId;

	@Column(name = "RATE_PER_PCS", nullable = false, precision = 12, scale = 2)
	private BigDecimal ratePerPcs;

	@Column(name = "RATE_PER_CASE", nullable = false, precision = 12, scale = 2)
	private BigDecimal ratePerCase;

	@Column(name = "MINIMUM_QUANTITY", nullable = false)
	private Integer minimumQuantity;

	@Column(name = "PRODUCTION_COST", nullable = false, precision = 12, scale = 2)
	private BigDecimal productionCost;

	@Column(name = "PRODUCTION_COST_BASIS", nullable = false)
	private Integer productionCostBasis;

	@Column(name = "VAT", nullable = false, precision = 10, scale = 2)
	private BigDecimal vat;

	@Column(name = "SD", nullable = false, precision = 10, scale = 2)
	private BigDecimal sd;

	@Column(name = "APPLIES_IN", nullable = false)
	private Integer appliesIn;

	@Column(name = "STATUS", nullable = false)
	private Integer status;

	@CreationTimestamp
	@Column(name = "CREATED_AT", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT", nullable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "DELETED_AT")
	private OffsetDateTime deletedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getRatePerPcs() {
		return ratePerPcs;
	}

	public void setRatePerPcs(BigDecimal ratePerPcs) {
		this.ratePerPcs = ratePerPcs;
	}

	public BigDecimal getRatePerCase() {
		return ratePerCase;
	}

	public void setRatePerCase(BigDecimal ratePerCase) {
		this.ratePerCase = ratePerCase;
	}

	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public BigDecimal getProductionCost() {
		return productionCost;
	}

	public void setProductionCost(BigDecimal productionCost) {
		this.productionCost = productionCost;
	}

	public Integer getProductionCostBasis() {
		return productionCostBasis;
	}

	public void setProductionCostBasis(Integer productionCostBasis) {
		this.productionCostBasis = productionCostBasis;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getSd() {
		return sd;
	}

	public void setSd(BigDecimal sd) {
		this.sd = sd;
	}

	public Integer getAppliesIn() {
		return appliesIn;
	}

	public void setAppliesIn(Integer appliesIn) {
		this.appliesIn = appliesIn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public OffsetDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(OffsetDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
}
