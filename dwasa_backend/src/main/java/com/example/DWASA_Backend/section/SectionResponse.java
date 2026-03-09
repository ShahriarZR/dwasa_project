package com.example.DWASA_Backend.section;

import java.time.OffsetDateTime;
import java.util.List;

public class SectionResponse {
	private final Long id;
	private final String name;
	private final String location;
	private final String remarks;
	private final Long createdBy;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;
	private final List<SectionItemResponse> input;
	private final List<SectionItemResponse> output;

	public SectionResponse(
			Long id,
			String name,
			String location,
			String remarks,
			Long createdBy,
			Long updatedBy,
			OffsetDateTime deletedAt,
			List<SectionItemResponse> input,
			List<SectionItemResponse> output) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.deletedAt = deletedAt;
		this.input = input;
		this.output = output;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getRemarks() {
		return remarks;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public OffsetDateTime getDeletedAt() {
		return deletedAt;
	}

	public List<SectionItemResponse> getInput() {
		return input;
	}

	public List<SectionItemResponse> getOutput() {
		return output;
	}
}