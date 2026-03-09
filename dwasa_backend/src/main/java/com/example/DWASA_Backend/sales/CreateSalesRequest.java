package com.example.DWASA_Backend.sales;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateSalesRequest {
	@NotNull
	private Long productId;

	@NotNull
	private BigDecimal ratePerPcs;

	@NotNull
	private BigDecimal ratePerCase;

	@NotNull
	private Integer minimumQuantity;

	@NotNull
	private BigDecimal productionCost;

	@NotNull
	private Integer productionCostBasis;

	@NotNull
	private BigDecimal vat;

	@NotNull
	private BigDecimal sd;

	@NotNull
	private Integer appliesIn;

	@NotNull
	private Integer status;

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
}
