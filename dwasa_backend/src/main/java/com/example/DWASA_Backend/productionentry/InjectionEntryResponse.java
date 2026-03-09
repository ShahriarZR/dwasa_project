package com.example.DWASA_Backend.productionentry;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class InjectionEntryResponse {
	private final Long id;
	private final Long sectionId;
	private final LocalDate entryDate;
	private final String entryTime;
	private final String remarks;
	private final OffsetDateTime createdAt;
	private final Long createdBy;
	private final OffsetDateTime updatedAt;
	private final Long updatedBy;
	private final OffsetDateTime deletedAt;
	private final Long deletedBy;
	private final List<InjectionEntryItemResponse> items;

	public InjectionEntryResponse(InjectionEntry entry, List<InjectionEntryItemResponse> items) {
		this.id = entry.getId();
		this.sectionId = entry.getSectionId();
		this.entryDate = entry.getEntryDate();
		this.entryTime = entry.getEntryTime();
		this.remarks = entry.getRemarks();
		this.createdAt = entry.getCreatedAt();
		this.createdBy = entry.getCreatedBy();
		this.updatedAt = entry.getUpdatedAt();
		this.updatedBy = entry.getUpdatedBy();
		this.deletedAt = entry.getDeletedAt();
		this.deletedBy = entry.getDeletedBy();
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public OffsetDateTime getDeletedAt() {
		return deletedAt;
	}

	public Long getDeletedBy() {
		return deletedBy;
	}

	public List<InjectionEntryItemResponse> getItems() {
		return items;
	}
}

