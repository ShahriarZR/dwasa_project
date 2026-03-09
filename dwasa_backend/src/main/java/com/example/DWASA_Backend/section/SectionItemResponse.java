package com.example.DWASA_Backend.section;

public class SectionItemResponse {
	private final Long ingradientId;
	private final String ingradientName;
	private final Long unitId;
	private final String unitName;

	public SectionItemResponse(Long ingradientId, String ingradientName, Long unitId, String unitName) {
		this.ingradientId = ingradientId;
		this.ingradientName = ingradientName;
		this.unitId = unitId;
		this.unitName = unitName;
	}

	public Long getIngradientId() {
		return ingradientId;
	}

	public String getIngradientName() {
		return ingradientName;
	}

	public Long getUnitId() {
		return unitId;
	}

	public String getUnitName() {
		return unitName;
	}
}