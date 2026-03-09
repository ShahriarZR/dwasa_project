package com.example.DWASA_Backend.section;

import com.example.DWASA_Backend.ingradient.Ingradient;
import com.example.DWASA_Backend.ingradient.IngradientRepository;
import com.example.DWASA_Backend.unit.Unit;
import com.example.DWASA_Backend.unit.UnitRepository;
import com.example.DWASA_Backend.user.User;
import com.example.DWASA_Backend.user.UserRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SectionService {
	private final SectionRepository sectionRepository;
	private final SectionIngradientUnitRepository sectionIngradientUnitRepository;
	private final IngradientRepository ingradientRepository;
	private final UnitRepository unitRepository;
	private final UserRepository userRepository;

	public SectionService(
			SectionRepository sectionRepository,
			SectionIngradientUnitRepository sectionIngradientUnitRepository,
			IngradientRepository ingradientRepository,
			UnitRepository unitRepository,
			UserRepository userRepository) {
		this.sectionRepository = sectionRepository;
		this.sectionIngradientUnitRepository = sectionIngradientUnitRepository;
		this.ingradientRepository = ingradientRepository;
		this.unitRepository = unitRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public SectionResponse createSection(CreateSectionRequest request) {
		Long userId = resolveAuthenticatedUserId();

		Section section = new Section();
		section.setName(request.getName().trim());
		section.setLocation(trimToNull(request.getLocation()));
		section.setRemarks(trimToNull(request.getRemarks()));
		section.setCreatedBy(userId);
		section.setUpdatedBy(userId);

		Section savedSection = sectionRepository.save(section);
		replaceSectionRows(savedSection.getId(), request.getInput(), request.getOutput());
		return getSection(savedSection.getId());
	}

	@Transactional(readOnly = true)
	public List<SectionResponse> getAllSections() {
		return sectionRepository.findAllByDeletedAtIsNullOrderByIdDesc().stream()
				.map(section -> buildResponse(section, sectionIngradientUnitRepository.findAllBySectionIdOrderByIdAsc(section.getId())))
				.toList();
	}

	@Transactional(readOnly = true)
	public SectionResponse getSection(Long sectionId) {
		Section section = sectionRepository.findByIdAndDeletedAtIsNull(sectionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));

		List<SectionIngradientUnit> rows = sectionIngradientUnitRepository.findAllBySectionIdOrderByIdAsc(sectionId);
		return buildResponse(section, rows);
	}

	@Transactional
	public SectionResponse updateSection(Long sectionId, UpdateSectionRequest request) {
		Section section = sectionRepository.findByIdAndDeletedAtIsNull(sectionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));

		if (request.getName() != null) {
			String updatedName = request.getName().trim();
			if (updatedName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NAME cannot be blank");
			}
			section.setName(updatedName);
		}

		if (request.getLocation() != null) {
			section.setLocation(trimToNull(request.getLocation()));
		}

		if (request.getRemarks() != null) {
			section.setRemarks(trimToNull(request.getRemarks()));
		}

		section.setUpdatedBy(resolveAuthenticatedUserId());

		sectionRepository.save(section);

		if (request.getInput() != null || request.getOutput() != null) {
			replaceSectionRows(sectionId, request.getInput(), request.getOutput());
		}

		return getSection(sectionId);
	}

	@Transactional
	public void deleteSection(Long sectionId) {
		Section section = sectionRepository.findByIdAndDeletedAtIsNull(sectionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));

		section.setDeletedAt(OffsetDateTime.now());
		section.setUpdatedBy(resolveAuthenticatedUserId());
		sectionRepository.save(section);
	}

	private void replaceSectionRows(
			Long sectionId,
			SectionFlowSelectionRequest inputRequest,
			SectionFlowSelectionRequest outputRequest) {
		sectionIngradientUnitRepository.deleteAllBySectionId(sectionId);

		List<SectionIngradientUnit> rows = new ArrayList<>();
		rows.addAll(expandRows(sectionId, SectionFlowType.INPUT, inputRequest));
		rows.addAll(expandRows(sectionId, SectionFlowType.OUTPUT, outputRequest));

		if (!rows.isEmpty()) {
			sectionIngradientUnitRepository.saveAll(rows);
		}
	}

	private List<SectionIngradientUnit> expandRows(
			Long sectionId,
			SectionFlowType flowType,
			SectionFlowSelectionRequest request) {
		if (request == null || request.getIngradientIds() == null || request.getIngradientIds().isEmpty()) {
			return List.of();
		}

		List<Long> ingradientIds = request.getIngradientIds();
		List<Long> unitIds = request.getUnitIds();
		if (unitIds == null || unitIds.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, flowType + " unitIds are required");
		}

		if (!(unitIds.size() == 1 || unitIds.size() == ingradientIds.size())) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					flowType + " unitIds must have one item or same length as ingradientIds");
		}

		validateIngradientIds(ingradientIds, flowType);
		validateUnitIds(unitIds, flowType);

		List<SectionIngradientUnit> rows = new ArrayList<>();
		for (int i = 0; i < ingradientIds.size(); i++) {
			Long unitId = unitIds.size() == 1 ? unitIds.get(0) : unitIds.get(i);

			SectionIngradientUnit row = new SectionIngradientUnit();
			row.setSectionId(sectionId);
			row.setFlowType(flowType);
			row.setIngradientId(ingradientIds.get(i));
			row.setUnitId(unitId);
			rows.add(row);
		}

		return rows;
	}

	private SectionResponse buildResponse(Section section, List<SectionIngradientUnit> rows) {
		Map<Long, Ingradient> ingradientMap = loadIngradients(rows);
		Map<Long, Unit> unitMap = loadUnits(rows);

		List<SectionItemResponse> input = new ArrayList<>();
		List<SectionItemResponse> output = new ArrayList<>();

		for (SectionIngradientUnit row : rows) {
			Ingradient ingradient = ingradientMap.get(row.getIngradientId());
			Unit unit = unitMap.get(row.getUnitId());

			SectionItemResponse item = new SectionItemResponse(
					row.getIngradientId(),
					ingradient == null ? null : ingradient.getName(),
					row.getUnitId(),
					unit == null ? null : unit.getName());

			if (row.getFlowType() == SectionFlowType.INPUT) {
				input.add(item);
			} else {
				output.add(item);
			}
		}

		return new SectionResponse(
				section.getId(),
				section.getName(),
				section.getLocation(),
				section.getRemarks(),
				section.getCreatedBy(),
				section.getUpdatedBy(),
				section.getDeletedAt(),
				input,
				output);
	}

	private Long resolveAuthenticatedUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authentication");
		}

		String empId = String.valueOf(authentication.getPrincipal());
		return userRepository.findByEmpId(empId)
				.map(User::getId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
	}

	private void validateIngradientIds(List<Long> ids, SectionFlowType flowType) {
		for (Long id : ids) {
			if (!ingradientRepository.existsById(id)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, flowType + " ingradientId not found: " + id);
			}
		}
	}

	private void validateUnitIds(List<Long> ids, SectionFlowType flowType) {
		for (Long id : ids) {
			if (!unitRepository.existsById(id)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, flowType + " unitId not found: " + id);
			}
		}
	}

	private Map<Long, Ingradient> loadIngradients(List<SectionIngradientUnit> rows) {
		Map<Long, Ingradient> map = new HashMap<>();
		for (SectionIngradientUnit row : rows) {
			ingradientRepository.findById(row.getIngradientId())
					.ifPresent(ingradient -> map.put(row.getIngradientId(), ingradient));
		}
		return map;
	}

	private Map<Long, Unit> loadUnits(List<SectionIngradientUnit> rows) {
		Map<Long, Unit> map = new HashMap<>();
		for (SectionIngradientUnit row : rows) {
			unitRepository.findById(row.getUnitId())
					.ifPresent(unit -> map.put(row.getUnitId(), unit));
		}
		return map;
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}