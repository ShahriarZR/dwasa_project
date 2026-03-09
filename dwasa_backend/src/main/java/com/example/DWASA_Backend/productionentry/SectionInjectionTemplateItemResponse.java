package com.example.DWASA_Backend.productionentry;

import com.example.DWASA_Backend.section.SectionFlowType;

public class SectionInjectionTemplateItemResponse {
	private final Long sectionIngradientUnitId;
	private final Long ingradientId;
	private final Long unitId;
	private final SectionFlowType flowType;

	public SectionInjectionTemplateItemResponse(
			Long sectionIngradientUnitId,
			Long ingradientId,
			Long unitId,
			SectionFlowType flowType) {
		this.sectionIngradientUnitId = sectionIngradientUnitId;
		this.ingradientId = ingradientId;
		this.unitId = unitId;
		this.flowType = flowType;
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
}

