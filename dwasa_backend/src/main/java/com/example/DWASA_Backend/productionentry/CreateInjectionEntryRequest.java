package com.example.DWASA_Backend.productionentry;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class CreateInjectionEntryRequest {
	@NotNull
	private Long sectionId;

	@NotNull
	private LocalDate entryDate;

	@NotBlank
	private String entryTime;

	private String remarks;

	@NotEmpty
	@Valid
	private List<InjectionEntryItemRequest> items;

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<InjectionEntryItemRequest> getItems() {
		return items;
	}

	public void setItems(List<InjectionEntryItemRequest> items) {
		this.items = items;
	}
}

