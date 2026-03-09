package com.example.DWASA_Backend.production;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/productions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductionController {
	private final ProductionService productionService;

	public ProductionController(ProductionService productionService) {
		this.productionService = productionService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProductionResponse createProduction(@Valid @RequestBody CreateProductionRequest request) {
		return productionService.createProduction(request);
	}

	@GetMapping
	public List<ProductionResponse> getAllProductions() {
		return productionService.getAllProductions();
	}

	@GetMapping(path = "/{productionId}")
	public ProductionResponse getProduction(@PathVariable Long productionId) {
		return productionService.getProduction(productionId);
	}

	@PutMapping(path = "/{productionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ProductionResponse updateProduction(
			@PathVariable Long productionId,
			@Valid @RequestBody UpdateProductionRequest request) {
		return productionService.updateProduction(productionId, request);
	}

	@DeleteMapping(path = "/{productionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduction(@PathVariable Long productionId) {
		productionService.deleteProduction(productionId);
	}
}
