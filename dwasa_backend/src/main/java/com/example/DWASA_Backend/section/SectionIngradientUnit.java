package com.example.DWASA_Backend.section;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SECTION_INGRADIENT_UNIT")
public class SectionIngradientUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "SECTION_ID", nullable = false)
	private Long sectionId;

	@Enumerated(EnumType.STRING)
	@Column(name = "FLOW_TYPE", nullable = false, length = 10)
	private SectionFlowType flowType;

	@Column(name = "INGRADIENT_ID", nullable = false)
	private Long ingradientId;

	@Column(name = "UNIT_ID", nullable = false)
	private Long unitId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public SectionFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(SectionFlowType flowType) {
		this.flowType = flowType;
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
}