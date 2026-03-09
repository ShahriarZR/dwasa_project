package com.example.DWASA_Backend.productionentry;

import com.example.DWASA_Backend.ingradient.IngradientRepository;
import com.example.DWASA_Backend.section.Section;
import com.example.DWASA_Backend.section.SectionIngradientUnit;
import com.example.DWASA_Backend.section.SectionIngradientUnitRepository;
import com.example.DWASA_Backend.section.SectionRepository;
import com.example.DWASA_Backend.unit.UnitRepository;
import com.example.DWASA_Backend.user.User;
import com.example.DWASA_Backend.user.UserRepository;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InjectionEntryService {
	private static final Pattern ENTRY_TIME_PATTERN =
			Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$");

	private final InjectionEntryRepository injectionEntryRepository;
	private final InjectionEntryItemRepository injectionEntryItemRepository;
	private final SectionRepository sectionRepository;
	private final SectionIngradientUnitRepository sectionIngradientUnitRepository;
	private final IngradientRepository ingradientRepository;
	private final UnitRepository unitRepository;
	private final UserRepository userRepository;

	public InjectionEntryService(
			InjectionEntryRepository injectionEntryRepository,
			InjectionEntryItemRepository injectionEntryItemRepository,
			SectionRepository sectionRepository,
			SectionIngradientUnitRepository sectionIngradientUnitRepository,
			IngradientRepository ingradientRepository,
			UnitRepository unitRepository,
			UserRepository userRepository) {
		this.injectionEntryRepository = injectionEntryRepository;
		this.injectionEntryItemRepository = injectionEntryItemRepository;
		this.sectionRepository = sectionRepository;
		this.sectionIngradientUnitRepository = sectionIngradientUnitRepository;
		this.ingradientRepository = ingradientRepository;
		this.unitRepository = unitRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public InjectionEntryResponse createInjectionEntry(CreateInjectionEntryRequest request) {
		Section section = validateSection(request.getSectionId());
		String entryTime = normalizeAndValidateEntryTime(request.getEntryTime());
		Long userId = resolveAuthenticatedUserId();

		InjectionEntry entry = new InjectionEntry();
		entry.setSectionId(section.getId());
		entry.setEntryDate(request.getEntryDate());
		entry.setEntryTime(entryTime);
		entry.setRemarks(trimToNull(request.getRemarks()));
		entry.setCreatedBy(userId);
		entry.setUpdatedBy(userId);

		InjectionEntry savedEntry = injectionEntryRepository.save(entry);
		syncEntryItems(savedEntry, request.getItems(), userId);
		return toResponse(savedEntry);
	}

	@Transactional(readOnly = true)
	public List<InjectionEntryResponse> getAllInjectionEntries() {
		return injectionEntryRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public InjectionEntryResponse getInjectionEntry(Long injectionEntryId) {
		InjectionEntry entry = getActiveInjectionEntry(injectionEntryId);
		return toResponse(entry);
	}

	@Transactional
	public InjectionEntryResponse updateInjectionEntry(Long injectionEntryId, UpdateInjectionEntryRequest request) {
		InjectionEntry entry = getActiveInjectionEntry(injectionEntryId);

		if (request.getSectionId() != null) {
			Section section = validateSection(request.getSectionId());
			entry.setSectionId(section.getId());
		}
		if (request.getEntryDate() != null) {
			entry.setEntryDate(request.getEntryDate());
		}
		if (request.getEntryTime() != null) {
			entry.setEntryTime(normalizeAndValidateEntryTime(request.getEntryTime()));
		}
		if (request.getRemarks() != null) {
			entry.setRemarks(trimToNull(request.getRemarks()));
		}

		Long userId = resolveAuthenticatedUserId();
		entry.setUpdatedBy(userId);
		entry.setUpdatedAt(OffsetDateTime.now());
		InjectionEntry savedEntry = injectionEntryRepository.save(entry);

		if (request.getItems() != null) {
			syncEntryItems(savedEntry, request.getItems(), userId);
		}

		return toResponse(savedEntry);
	}

	@Transactional
	public void deleteInjectionEntry(Long injectionEntryId) {
		InjectionEntry entry = getActiveInjectionEntry(injectionEntryId);
		Long userId = resolveAuthenticatedUserId();
		OffsetDateTime now = OffsetDateTime.now();

		entry.setDeletedAt(now);
		entry.setDeletedBy(userId);
		entry.setUpdatedBy(userId);
		entry.setUpdatedAt(now);
		injectionEntryRepository.save(entry);

		List<InjectionEntryItem> activeItems =
				injectionEntryItemRepository.findAllByInjectionEntryIdAndDeletedAtIsNullOrderByIdAsc(entry.getId());
		for (InjectionEntryItem item : activeItems) {
			item.setDeletedAt(now);
			item.setDeletedBy(userId);
			item.setUpdatedBy(userId);
		}
		injectionEntryItemRepository.saveAll(activeItems);
	}

	@Transactional(readOnly = true)
	public List<SectionInjectionTemplateItemResponse> getSectionTemplate(Long sectionId) {
		validateSection(sectionId);
		return sectionIngradientUnitRepository.findAllBySectionIdOrderByIdAsc(sectionId)
				.stream()
				.map(item -> new SectionInjectionTemplateItemResponse(
						item.getId(),
						item.getIngradientId(),
						item.getUnitId(),
						item.getFlowType()))
				.toList();
	}

	private InjectionEntryResponse toResponse(InjectionEntry entry) {
		List<InjectionEntryItemResponse> items =
				injectionEntryItemRepository.findAllByInjectionEntryIdAndDeletedAtIsNullOrderByIdAsc(entry.getId())
						.stream()
						.map(InjectionEntryItemResponse::new)
						.toList();
		return new InjectionEntryResponse(entry, items);
	}

	private InjectionEntry getActiveInjectionEntry(Long injectionEntryId) {
		return injectionEntryRepository.findByIdAndDeletedAtIsNull(injectionEntryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Injection entry not found"));
	}

	private Section validateSection(Long sectionId) {
		return sectionRepository.findByIdAndDeletedAtIsNull(sectionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SECTION_ID not found"));
	}

	private String normalizeAndValidateEntryTime(String entryTime) {
		if (entryTime == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ENTRY_TIME is required");
		}
		String normalized = entryTime.trim();
		if (!ENTRY_TIME_PATTERN.matcher(normalized).matches()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ENTRY_TIME must be HH:mm or HH:mm:ss");
		}
		return normalized;
	}

	private void syncEntryItems(InjectionEntry entry, List<InjectionEntryItemRequest> requests, Long userId) {
		if (requests == null || requests.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one item is required");
		}

		Map<Long, InjectionEntryItem> existingBySectionItem = new HashMap<>();
		for (InjectionEntryItem existing :
				injectionEntryItemRepository.findAllByInjectionEntryIdOrderByIdAsc(entry.getId())) {
			existingBySectionItem.put(existing.getSectionIngradientUnitId(), existing);
		}

		Set<Long> requestedSectionItemIds = new HashSet<>();
		for (InjectionEntryItemRequest request : requests) {
			Long sectionItemId = request.getSectionIngradientUnitId();
			if (!requestedSectionItemIds.add(sectionItemId)) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST,
						"Duplicate SECTION_INGRADIENT_UNIT_ID in items: " + sectionItemId);
			}

			SectionIngradientUnit sectionItem = sectionIngradientUnitRepository.findById(sectionItemId)
					.orElseThrow(() -> new ResponseStatusException(
							HttpStatus.BAD_REQUEST,
							"SECTION_INGRADIENT_UNIT_ID not found: " + sectionItemId));
			if (!entry.getSectionId().equals(sectionItem.getSectionId())) {
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST,
						"SECTION_INGRADIENT_UNIT_ID " + sectionItemId + " does not belong to SECTION_ID " + entry.getSectionId());
			}

			ingradientRepository.findByIdAndDeletedAtIsNull(sectionItem.getIngradientId())
					.orElseThrow(() -> new ResponseStatusException(
							HttpStatus.BAD_REQUEST,
							"INGRADIENT_ID not found: " + sectionItem.getIngradientId()));
			unitRepository.findByIdAndDeletedAtIsNull(sectionItem.getUnitId())
					.orElseThrow(() -> new ResponseStatusException(
							HttpStatus.BAD_REQUEST,
							"UNIT_ID not found: " + sectionItem.getUnitId()));

			InjectionEntryItem item = existingBySectionItem.get(sectionItemId);
			if (item == null) {
				item = new InjectionEntryItem();
				item.setInjectionEntryId(entry.getId());
				item.setSectionIngradientUnitId(sectionItemId);
				item.setCreatedBy(userId);
			}

			item.setIngradientId(sectionItem.getIngradientId());
			item.setUnitId(sectionItem.getUnitId());
			item.setFlowType(sectionItem.getFlowType());
			item.setQuantity(request.getQuantity());
			item.setUpdatedBy(userId);
			item.setDeletedAt(null);
			item.setDeletedBy(null);

			injectionEntryItemRepository.save(item);
		}

		OffsetDateTime now = OffsetDateTime.now();
		for (Map.Entry<Long, InjectionEntryItem> existing : existingBySectionItem.entrySet()) {
			if (!requestedSectionItemIds.contains(existing.getKey())) {
				InjectionEntryItem item = existing.getValue();
				if (item.getDeletedAt() == null) {
					item.setDeletedAt(now);
					item.setDeletedBy(userId);
					item.setUpdatedBy(userId);
					injectionEntryItemRepository.save(item);
				}
			}
		}
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

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}

