package com.example.DWASA_Backend.section;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SectionFlowSelectionRequest {
	@NotEmpty
	private List<Long> ingradientIds;

	@NotNull
	private List<Long> unitIds;

	public List<Long> getIngradientIds() {
		return ingradientIds;
	}

	public void setIngradientIds(List<Long> ingradientIds) {
		this.ingradientIds = ingradientIds;
	}

	public List<Long> getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(List<Long> unitIds) {
		this.unitIds = unitIds;
	}
}