package com.example.DWASA_Backend.wastage;

public class UpdateWastageRequest {
	private Long productId;
	private Long ingradientId;
	private Long sectionId;
	private Integer unit;
	private String remarks;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getIngradientId() {
		return ingradientId;
	}

	public void setIngradientId(Long ingradientId) {
		this.ingradientId = ingradientId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
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
}