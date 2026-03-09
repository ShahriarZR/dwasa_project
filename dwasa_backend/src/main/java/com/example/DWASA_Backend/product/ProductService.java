package com.example.DWASA_Backend.product;

import com.example.DWASA_Backend.ingradient.Ingradient;
import com.example.DWASA_Backend.ingradient.IngradientRepository;
import com.example.DWASA_Backend.user.User;
import com.example.DWASA_Backend.user.UserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final IngradientRepository ingradientRepository;
	private final UserRepository userRepository;
	private final Integer finishedGoodsAreaOfUse;

	public ProductService(
			ProductRepository productRepository,
			IngradientRepository ingradientRepository,
			UserRepository userRepository,
			@Value("${app.ingradient.area-of-use.finished-goods:1}") Integer finishedGoodsAreaOfUse) {
		this.productRepository = productRepository;
		this.ingradientRepository = ingradientRepository;
		this.userRepository = userRepository;
		this.finishedGoodsAreaOfUse = finishedGoodsAreaOfUse;
	}

	@Transactional
	public ProductResponse createProduct(CreateProductRequest request) {
		String productCode = request.getProductCode().trim();
		if (productRepository.findByProductCodeAndDeletedAtIsNull(productCode).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "PRODUCT_CODE already exists");
		}

		Long userId = resolveAuthenticatedUserId();

		ProductType product = new ProductType();
		product.setProductCode(productCode);
		String productName = request.getName().trim();
		product.setName(productName);
		product.setCapacityMl(request.getCapacityMl());
		if (request.getUnit() != null) {
			product.setUnit(request.getUnit());
		}
		if (request.getRemarks() != null) {
			product.setRemarks(trimToNull(request.getRemarks()));
		}
		product.setStatus(request.getStatus() == null ? ProductStatus.ACTIVE : request.getStatus());
		product.setCreatedBy(userId);
		product.setUpdatedBy(userId);

		ProductType saved = productRepository.save(product);

		Ingradient ingradient = new Ingradient();
		ingradient.setName(productName);
		ingradient.setFullName(productName);
		ingradient.setAreaOfUse(finishedGoodsAreaOfUse);
		ingradient.setCreatedBy(userId);
		ingradient.setUpdatedBy(userId);
		ingradientRepository.save(ingradient);

		return new ProductResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getAllProducts() {
		return productRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(ProductResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public ProductResponse getProduct(Long productId) {
		ProductType product = productRepository.findByIdAndDeletedAtIsNull(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
		return new ProductResponse(product);
	}

	@Transactional
	public ProductResponse updateProduct(Long productId, UpdateProductRequest request) {
		ProductType product = productRepository.findByIdAndDeletedAtIsNull(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

		if (request.getProductCode() != null) {
			String updatedProductCode = request.getProductCode().trim();
			if (updatedProductCode.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PRODUCT_CODE cannot be blank");
			}

			productRepository.findByProductCodeAndDeletedAtIsNull(updatedProductCode)
					.filter(existing -> !existing.getId().equals(product.getId()))
					.ifPresent(existing -> {
						throw new ResponseStatusException(HttpStatus.CONFLICT, "PRODUCT_CODE already exists");
					});
			product.setProductCode(updatedProductCode);
		}

		if (request.getName() != null) {
			String updatedName = request.getName().trim();
			if (updatedName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NAME cannot be blank");
			}
			product.setName(updatedName);
		}

		if (request.getCapacityMl() != null) {
			product.setCapacityMl(request.getCapacityMl());
		}

		if (request.getUnit() != null) {
			product.setUnit(request.getUnit());
		}

		if (request.getRemarks() != null) {
			product.setRemarks(trimToNull(request.getRemarks()));
		}

		if (request.getStatus() != null) {
			product.setStatus(request.getStatus());
		}

		product.setUpdatedBy(resolveAuthenticatedUserId());
		ProductType saved = productRepository.save(product);
		return new ProductResponse(saved);
	}

	@Transactional
	public void deleteProduct(Long productId) {
		ProductType product = productRepository.findByIdAndDeletedAtIsNull(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

		product.setDeletedAt(OffsetDateTime.now());
		product.setStatus(ProductStatus.INACTIVE);
		product.setUpdatedBy(resolveAuthenticatedUserId());
		productRepository.save(product);
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