package com.example.DWASA_Backend.unit;

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
@RequestMapping(path = "/api/units", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnitController {
	private final UnitService unitService;

	public UnitController(UnitService unitService) {
		this.unitService = unitService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UnitResponse createUnit(@Valid @RequestBody CreateUnitRequest request) {
		return unitService.createUnit(request);
	}

	@GetMapping
	public List<UnitResponse> getAllUnits() {
		return unitService.getAllUnits();
	}

	@GetMapping(path = "/{unitId}")
	public UnitResponse getUnit(@PathVariable Long unitId) {
		return unitService.getUnit(unitId);
	}

	@PutMapping(path = "/{unitId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UnitResponse updateUnit(
			@PathVariable Long unitId,
			@Valid @RequestBody UpdateUnitRequest request) {
		return unitService.updateUnit(unitId, request);
	}

	@DeleteMapping(path = "/{unitId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUnit(@PathVariable Long unitId) {
		unitService.deleteUnit(unitId);
	}
}