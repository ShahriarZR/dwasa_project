package com.example.DWASA_Backend.sales;

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
public class SalesService {
	private final SalesRepository salesRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public SalesService(
			SalesRepository salesRepository,
			ProductRepository productRepository,
			UserRepository userRepository) {
		this.salesRepository = salesRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public SalesResponse createSales(CreateSalesRequest request) {
		validateProductId(request.getProductId());

		Long userId = resolveAuthenticatedUserId();

		Sales sales = new Sales();
		sales.setProductId(request.getProductId());
		sales.setRatePerPcs(request.getRatePerPcs());
		sales.setRatePerCase(request.getRatePerCase());
		sales.setMinimumQuantity(request.getMinimumQuantity());
		sales.setProductionCost(request.getProductionCost());
		sales.setProductionCostBasis(request.getProductionCostBasis());
		sales.setVat(request.getVat());
		sales.setSd(request.getSd());
		sales.setAppliesIn(request.getAppliesIn());
		sales.setStatus(request.getStatus());
		sales.setCreatedBy(userId);
		sales.setUpdatedBy(userId);

		Sales saved = salesRepository.save(sales);
		return new SalesResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<SalesResponse> getAllSales() {
		return salesRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(SalesResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public SalesResponse getSales(Long salesId) {
		Sales sales = salesRepository.findByIdAndDeletedAtIsNull(salesId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales not found"));
		return new SalesResponse(sales);
	}

	@Transactional
	public SalesResponse updateSales(Long salesId, UpdateSalesRequest request) {
		Sales sales = salesRepository.findByIdAndDeletedAtIsNull(salesId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales not found"));

		if (request.getProductId() != null) {
			validateProductId(request.getProductId());
			sales.setProductId(request.getProductId());
		}

		if (request.getRatePerPcs() != null) {
			sales.setRatePerPcs(request.getRatePerPcs());
		}

		if (request.getRatePerCase() != null) {
			sales.setRatePerCase(request.getRatePerCase());
		}

		if (request.getMinimumQuantity() != null) {
			sales.setMinimumQuantity(request.getMinimumQuantity());
		}

		if (request.getProductionCost() != null) {
			sales.setProductionCost(request.getProductionCost());
		}

		if (request.getProductionCostBasis() != null) {
			sales.setProductionCostBasis(request.getProductionCostBasis());
		}

		if (request.getVat() != null) {
			sales.setVat(request.getVat());
		}

		if (request.getSd() != null) {
			sales.setSd(request.getSd());
		}

		if (request.getAppliesIn() != null) {
			sales.setAppliesIn(request.getAppliesIn());
		}

		if (request.getStatus() != null) {
			sales.setStatus(request.getStatus());
		}

		sales.setUpdatedBy(resolveAuthenticatedUserId());
		Sales saved = salesRepository.save(sales);
		return new SalesResponse(saved);
	}

	@Transactional
	public void deleteSales(Long salesId) {
		Sales sales = salesRepository.findByIdAndDeletedAtIsNull(salesId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales not found"));

		sales.setDeletedAt(OffsetDateTime.now());
		sales.setUpdatedBy(resolveAuthenticatedUserId());
		salesRepository.save(sales);
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
