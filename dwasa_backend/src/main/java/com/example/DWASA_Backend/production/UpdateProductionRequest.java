package com.example.DWASA_Backend.production;

public class UpdateProductionRequest {
	private Long productId;
	private Integer wrappingQuantity;
	private Integer status;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getWrappingQuantity() {
		return wrappingQuantity;
	}

	public void setWrappingQuantity(Integer wrappingQuantity) {
		this.wrappingQuantity = wrappingQuantity;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
