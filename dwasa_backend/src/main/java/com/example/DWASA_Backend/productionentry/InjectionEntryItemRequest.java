package com.example.DWASA_Backend.productionentry;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class InjectionEntryItemRequest {
	@NotNull
	private Long sectionIngradientUnitId;

	@NotNull
	@DecimalMin(value = "0", inclusive = true)
	private BigDecimal quantity;

	public Long getSectionIngradientUnitId() {
		return sectionIngradientUnitId;
	}

	public void setSectionIngradientUnitId(Long sectionIngradientUnitId) {
		this.sectionIngradientUnitId = sectionIngradientUnitId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}

