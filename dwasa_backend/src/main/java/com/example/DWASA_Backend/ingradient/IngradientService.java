package com.example.DWASA_Backend.ingradient;

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
public class IngradientService {
	private final IngradientRepository ingradientRepository;
	private final UserRepository userRepository;

	public IngradientService(IngradientRepository ingradientRepository, UserRepository userRepository) {
		this.ingradientRepository = ingradientRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public IngradientResponse createIngradient(CreateIngradientRequest request) {
		Long userId = resolveAuthenticatedUserId();

		Ingradient ingradient = new Ingradient();
		ingradient.setName(request.getName().trim());
		ingradient.setRemarks(trimToNull(request.getRemarks()));
		ingradient.setFullName(request.getFullName().trim());
		ingradient.setAreaOfUse(request.getAreaOfUse());
		ingradient.setCreatedBy(userId);
		ingradient.setUpdatedBy(userId);

		Ingradient saved = ingradientRepository.save(ingradient);
		return new IngradientResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<IngradientResponse> getAllIngradients() {
		return ingradientRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(IngradientResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public IngradientResponse getIngradient(Long ingradientId) {
		Ingradient ingradient = ingradientRepository.findByIdAndDeletedAtIsNull(ingradientId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingradient not found"));
		return new IngradientResponse(ingradient);
	}

	@Transactional
	public IngradientResponse updateIngradient(Long ingradientId, UpdateIngradientRequest request) {
		Ingradient ingradient = ingradientRepository.findByIdAndDeletedAtIsNull(ingradientId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingradient not found"));

		if (request.getName() != null) {
			String updatedName = request.getName().trim();
			if (updatedName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NAME cannot be blank");
			}
			ingradient.setName(updatedName);
		}

		if (request.getRemarks() != null) {
			ingradient.setRemarks(trimToNull(request.getRemarks()));
		}

		if (request.getFullName() != null) {
			String updatedFullName = request.getFullName().trim();
			if (updatedFullName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FULL_NAME cannot be blank");
			}
			ingradient.setFullName(updatedFullName);
		}

		if (request.getAreaOfUse() != null) {
			ingradient.setAreaOfUse(request.getAreaOfUse());
		}

		ingradient.setUpdatedBy(resolveAuthenticatedUserId());

		Ingradient saved = ingradientRepository.save(ingradient);
		return new IngradientResponse(saved);
	}

	@Transactional
	public void deleteIngradient(Long ingradientId) {
		Ingradient ingradient = ingradientRepository.findByIdAndDeletedAtIsNull(ingradientId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingradient not found"));

		ingradient.setDeletedAt(OffsetDateTime.now());
		ingradient.setUpdatedBy(resolveAuthenticatedUserId());
		ingradientRepository.save(ingradient);
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