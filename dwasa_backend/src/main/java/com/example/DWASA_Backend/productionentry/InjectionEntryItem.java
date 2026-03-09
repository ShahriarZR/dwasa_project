package com.example.DWASA_Backend.productionentry;

import com.example.DWASA_Backend.section.SectionFlowType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "INJECTION_ENTRY_ITEM")
public class InjectionEntryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "INJECTION_ENTRY_ID", nullable = false)
	private Long injectionEntryId;

	@Column(name = "SECTION_INGRADIENT_UNIT_ID", nullable = false)
	private Long sectionIngradientUnitId;

	@Column(name = "INGRADIENT_ID", nullable = false)
	private Long ingradientId;

	@Column(name = "UNIT_ID", nullable = false)
	private Long unitId;

	@Enumerated(EnumType.STRING)
	@Column(name = "FLOW_TYPE", nullable = false, length = 10)
	private SectionFlowType flowType;

	@Column(name = "QUANTITY", nullable = false, precision = 18, scale = 3)
	private BigDecimal quantity;

	@CreationTimestamp
	@Column(name = "CREATED_AT", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT", nullable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "DELETED_AT")
	private OffsetDateTime deletedAt;

	@Column(name = "DELETED_BY")
	private Long deletedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInjectionEntryId() {
		return injectionEntryId;
	}

	public void setInjectionEntryId(Long injectionEntryId) {
		this.injectionEntryId = injectionEntryId;
	}

	public Long getSectionIngradientUnitId() {
		return sectionIngradientUnitId;
	}

	public void setSectionIngradientUnitId(Long sectionIngradientUnitId) {
		this.sectionIngradientUnitId = sectionIngradientUnitId;
	}

	public Long getIngradientId() {
		return ingradientId;
	}

	public void setIngradientId(Long ingradientId) {
		this.ingradientId = ingradientId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public SectionFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(SectionFlowType flowType) {
		this.flowType = flowType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public OffsetDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(OffsetDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Long getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(Long deletedBy) {
		this.deletedBy = deletedBy;
	}
}

