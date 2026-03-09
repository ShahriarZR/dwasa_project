package com.example.DWASA_Backend.section;

import jakarta.validation.Valid;

public class UpdateSectionRequest {
	private String name;
	private String location;
	private String remarks;

	@Valid
	private SectionFlowSelectionRequest input;

	@Valid
	private SectionFlowSelectionRequest output;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SectionFlowSelectionRequest getInput() {
		return input;
	}

	public void setInput(SectionFlowSelectionRequest input) {
		this.input = input;
	}

	public SectionFlowSelectionRequest getOutput() {
		return output;
	}

	public void setOutput(SectionFlowSelectionRequest output) {
		this.output = output;
	}
}