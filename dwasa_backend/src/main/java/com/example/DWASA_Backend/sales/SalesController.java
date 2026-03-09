package com.example.DWASA_Backend.sales;

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
@RequestMapping(path = "/api/sales", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesController {
	private final SalesService salesService;

	public SalesController(SalesService salesService) {
		this.salesService = salesService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public SalesResponse createSales(@Valid @RequestBody CreateSalesRequest request) {
		return salesService.createSales(request);
	}

	@GetMapping
	public List<SalesResponse> getAllSales() {
		return salesService.getAllSales();
	}

	@GetMapping(path = "/{salesId}")
	public SalesResponse getSales(@PathVariable Long salesId) {
		return salesService.getSales(salesId);
	}

	@PutMapping(path = "/{salesId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public SalesResponse updateSales(
			@PathVariable Long salesId,
			@Valid @RequestBody UpdateSalesRequest request) {
		return salesService.updateSales(salesId, request);
	}

	@DeleteMapping(path = "/{salesId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSales(@PathVariable Long salesId) {
		salesService.deleteSales(salesId);
	}
}
