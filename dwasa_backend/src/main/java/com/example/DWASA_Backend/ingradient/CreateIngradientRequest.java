package com.example.DWASA_Backend.ingradient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateIngradientRequest {
	@NotBlank
	private String name;

	private String remarks;

	@NotBlank
	private String fullName;

	@NotNull
	private Integer areaOfUse;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getAreaOfUse() {
		return areaOfUse;
	}

	public void setAreaOfUse(Integer areaOfUse) {
		this.areaOfUse = areaOfUse;
	}
}