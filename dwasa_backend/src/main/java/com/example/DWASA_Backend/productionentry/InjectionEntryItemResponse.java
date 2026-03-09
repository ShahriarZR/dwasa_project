package com.example.DWASA_Backend.productionentry;

import com.example.DWASA_Backend.section.SectionFlowType;
import java.math.BigDecimal;

public class InjectionEntryItemResponse {
	private final Long id;
	private final Long sectionIngradientUnitId;
	private final Long ingradientId;
	private final Long unitId;
	private final SectionFlowType flowType;
	private final BigDecimal quantity;

	public InjectionEntryItemResponse(InjectionEntryItem item) {
		this.id = item.getId();
		this.sectionIngradientUnitId = item.getSectionIngradientUnitId();
		this.ingradientId = item.getIngradientId();
		this.unitId = item.getUnitId();
		this.flowType = item.getFlowType();
		this.quantity = item.getQuantity();
	}

	public Long getId() {
		return id;
	}

	public Long getSectionIngradientUnitId() {
		return sectionIngradientUnitId;
	}

	public Long getIngradientId() {
		return ingradientId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public SectionFlowType getFlowType() {
		return flowType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}
}

