package com.example.DWASA_Backend.wastage;

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
@RequestMapping(path = "/api/wastages", produces = MediaType.APPLICATION_JSON_VALUE)
public class WastageController {
	private final WastageService wastageService;

	public WastageController(WastageService wastageService) {
		this.wastageService = wastageService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public WastageResponse createWastage(@Valid @RequestBody CreateWastageRequest request) {
		return wastageService.createWastage(request);
	}

	@GetMapping
	public List<WastageResponse> getAllWastages() {
		return wastageService.getAllWastages();
	}

	@GetMapping(path = "/{wastageId}")
	public WastageResponse getWastage(@PathVariable Long wastageId) {
		return wastageService.getWastage(wastageId);
	}

	@PutMapping(path = "/{wastageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public WastageResponse updateWastage(
			@PathVariable Long wastageId,
			@Valid @RequestBody UpdateWastageRequest request) {
		return wastageService.updateWastage(wastageId, request);
	}

	@DeleteMapping(path = "/{wastageId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteWastage(@PathVariable Long wastageId) {
		wastageService.deleteWastage(wastageId);
	}
}