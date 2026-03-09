package com.example.DWASA_Backend.production;

import com.example.DWASA_Backend.product.ProductRepository;
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
public class ProductionService {
	private final ProductionRepository productionRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public ProductionService(
			ProductionRepository productionRepository,
			ProductRepository productRepository,
			UserRepository userRepository) {
		this.productionRepository = productionRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public ProductionResponse createProduction(CreateProductionRequest request) {
		validateProductId(request.getProductId());

		Long userId = resolveAuthenticatedUserId();

		Production production = new Production();
		production.setProductId(request.getProductId());
		production.setWrappingQuantity(request.getWrappingQuantity());
		production.setStatus(request.getStatus());
		production.setCreatedBy(userId);
		production.setUpdatedBy(userId);

		Production saved = productionRepository.save(production);
		return new ProductionResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<ProductionResponse> getAllProductions() {
		return productionRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(ProductionResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public ProductionResponse getProduction(Long productionId) {
		Production production = productionRepository.findByIdAndDeletedAtIsNull(productionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Production not found"));
		return new ProductionResponse(production);
	}

	@Transactional
	public ProductionResponse updateProduction(Long productionId, UpdateProductionRequest request) {
		Production production = productionRepository.findByIdAndDeletedAtIsNull(productionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Production not found"));

		if (request.getProductId() != null) {
			validateProductId(request.getProductId());
			production.setProductId(request.getProductId());
		}

		if (request.getWrappingQuantity() != null) {
			production.setWrappingQuantity(request.getWrappingQuantity());
		}

		if (request.getStatus() != null) {
			production.setStatus(request.getStatus());
		}

		production.setUpdatedBy(resolveAuthenticatedUserId());
		Production saved = productionRepository.save(production);
		return new ProductionResponse(saved);
	}

	@Transactional
	public void deleteProduction(Long productionId) {
		Production production = productionRepository.findByIdAndDeletedAtIsNull(productionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Production not found"));

		production.setDeletedAt(OffsetDateTime.now());
		production.setUpdatedBy(resolveAuthenticatedUserId());
		productionRepository.save(production);
	}

	private void validateProductId(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PRODUCT_ID not found");
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
}
