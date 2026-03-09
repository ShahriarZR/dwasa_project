package com.example.DWASA_Backend.wastage;

import com.example.DWASA_Backend.ingradient.IngradientRepository;
import com.example.DWASA_Backend.product.ProductRepository;
import com.example.DWASA_Backend.section.SectionRepository;
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
public class WastageService {
	private final WastageRepository wastageRepository;
	private final ProductRepository productRepository;
	private final IngradientRepository ingradientRepository;
	private final SectionRepository sectionRepository;
	private final UserRepository userRepository;

	public WastageService(
			WastageRepository wastageRepository,
			ProductRepository productRepository,
			IngradientRepository ingradientRepository,
			SectionRepository sectionRepository,
			UserRepository userRepository) {
		this.wastageRepository = wastageRepository;
		this.productRepository = productRepository;
		this.ingradientRepository = ingradientRepository;
		this.sectionRepository = sectionRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public WastageResponse createWastage(CreateWastageRequest request) {
		validateProductId(request.getProductId());
		validateIngradientId(request.getIngradientId());
		validateSectionId(request.getSectionId());

		Long userId = resolveAuthenticatedUserId();

		Wastage wastage = new Wastage();
		wastage.setProductId(request.getProductId());
		wastage.setIngradientId(request.getIngradientId());
		wastage.setSectionId(request.getSectionId());
		wastage.setUnit(request.getUnit());
		wastage.setRemarks(trimToNull(request.getRemarks()));
		wastage.setCreatedBy(userId);
		wastage.setUpdatedBy(userId);

		Wastage saved = wastageRepository.save(wastage);
		return new WastageResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<WastageResponse> getAllWastages() {
		return wastageRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(WastageResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public WastageResponse getWastage(Long wastageId) {
		Wastage wastage = wastageRepository.findByIdAndDeletedAtIsNull(wastageId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wastage not found"));
		return new WastageResponse(wastage);
	}

	@Transactional
	public WastageResponse updateWastage(Long wastageId, UpdateWastageRequest request) {
		Wastage wastage = wastageRepository.findByIdAndDeletedAtIsNull(wastageId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wastage not found"));

		if (request.getProductId() != null) {
			validateProductId(request.getProductId());
			wastage.setProductId(request.getProductId());
		}

		if (request.getIngradientId() != null) {
			validateIngradientId(request.getIngradientId());
			wastage.setIngradientId(request.getIngradientId());
		}

		if (request.getSectionId() != null) {
			validateSectionId(request.getSectionId());
			wastage.setSectionId(request.getSectionId());
		}

		if (request.getUnit() != null) {
			wastage.setUnit(request.getUnit());
		}

		if (request.getRemarks() != null) {
			wastage.setRemarks(trimToNull(request.getRemarks()));
		}

		wastage.setUpdatedBy(resolveAuthenticatedUserId());
		Wastage saved = wastageRepository.save(wastage);
		return new WastageResponse(saved);
	}

	@Transactional
	public void deleteWastage(Long wastageId) {
		Wastage wastage = wastageRepository.findByIdAndDeletedAtIsNull(wastageId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wastage not found"));

		wastage.setDeletedAt(OffsetDateTime.now());
		wastage.setUpdatedBy(resolveAuthenticatedUserId());
		wastageRepository.save(wastage);
	}

	private void validateProductId(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PRODUCT_ID not found");
		}
	}

	private void validateIngradientId(Long ingradientId) {
		if (!ingradientRepository.existsById(ingradientId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INGRADIENT_ID not found");
		}
	}

	private void validateSectionId(Long sectionId) {
		if (!sectionRepository.existsById(sectionId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SECTION_ID not found");
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