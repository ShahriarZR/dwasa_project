package com.example.DWASA_Backend.unit;

import com.example.DWASA_Backend.user.User;
import com.example.DWASA_Backend.user.UserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UnitService {
	private final UnitRepository unitRepository;
	private final UserRepository userRepository;

	public UnitService(UnitRepository unitRepository, UserRepository userRepository) {
		this.unitRepository = unitRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public UnitResponse createUnit(CreateUnitRequest request) {
		Long userId = resolveAuthenticatedUserId();

		Unit unit = new Unit();
		unit.setName(request.getName().trim());
		unit.setStatus(request.getStatus());
		unit.setRemarks(trimToNull(request.getRemarks()));
		unit.setCreatedBy(userId);
		unit.setUpdatedBy(userId);

		Unit saved = unitRepository.save(unit);
		return new UnitResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<UnitResponse> getAllUnits() {
		return unitRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(UnitResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public UnitResponse getUnit(Long unitId) {
		Unit unit = unitRepository.findByIdAndDeletedAtIsNull(unitId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));
		return new UnitResponse(unit);
	}

	@Transactional
	public UnitResponse updateUnit(Long unitId, UpdateUnitRequest request) {
		Unit unit = unitRepository.findByIdAndDeletedAtIsNull(unitId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

		if (request.getName() != null) {
			String updatedName = request.getName().trim();
			if (updatedName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NAME cannot be blank");
			}
			unit.setName(updatedName);
		}

		if (request.getStatus() != null) {
			unit.setStatus(request.getStatus());
		}

		if (request.getRemarks() != null) {
			unit.setRemarks(trimToNull(request.getRemarks()));
		}

		unit.setUpdatedBy(resolveAuthenticatedUserId());

		Unit saved = unitRepository.save(unit);
		return new UnitResponse(saved);
	}

	@Transactional
	public void deleteUnit(Long unitId) {
		Unit unit = unitRepository.findByIdAndDeletedAtIsNull(unitId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

		unit.setDeletedAt(OffsetDateTime.now());
		unit.setUpdatedBy(resolveAuthenticatedUserId());
		unitRepository.save(unit);
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