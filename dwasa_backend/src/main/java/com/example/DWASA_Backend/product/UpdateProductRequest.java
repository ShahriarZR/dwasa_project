package com.example.DWASA_Backend.product;

import java.math.BigDecimal;

public class UpdateProductRequest {
	private String productCode;
	private String name;
	private BigDecimal capacityMl;
	private Integer unit;
	private String remarks;
	private ProductStatus status;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCapacityMl() {
		return capacityMl;
	}

	public void setCapacityMl(BigDecimal capacityMl) {
		this.capacityMl = capacityMl;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}
}