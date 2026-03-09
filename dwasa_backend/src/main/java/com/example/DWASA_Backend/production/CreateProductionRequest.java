package com.example.DWASA_Backend.production;

import jakarta.validation.constraints.NotNull;

public class CreateProductionRequest {
	@NotNull
	private Long productId;

	@NotNull
	private Integer wrappingQuantity;

	@NotNull
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
