package com.example.DWASA_Backend.ingradient;

public class UpdateIngradientRequest {
	private String name;
	private String remarks;
	private String fullName;
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