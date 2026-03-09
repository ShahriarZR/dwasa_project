package com.example.DWASA_Backend.ingradient;

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
@RequestMapping(path = "/api/ingradients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngradientController {
	private final IngradientService ingradientService;

	public IngradientController(IngradientService ingradientService) {
		this.ingradientService = ingradientService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public IngradientResponse createIngradient(@Valid @RequestBody CreateIngradientRequest request) {
		return ingradientService.createIngradient(request);
	}

	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public IngradientResponse addIngradient(@Valid @RequestBody CreateIngradientRequest request) {
		return ingradientService.createIngradient(request);
	}

	@GetMapping
	public List<IngradientResponse> getAllIngradients() {
		return ingradientService.getAllIngradients();
	}

	@GetMapping(path = "/{ingradientId}")
	public IngradientResponse getIngradient(@PathVariable Long ingradientId) {
		return ingradientService.getIngradient(ingradientId);
	}

	@PutMapping(path = "/{ingradientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public IngradientResponse updateIngradient(
			@PathVariable Long ingradientId,
			@Valid @RequestBody UpdateIngradientRequest request) {
		return ingradientService.updateIngradient(ingradientId, request);
	}

	@DeleteMapping(path = "/{ingradientId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIngradient(@PathVariable Long ingradientId) {
		ingradientService.deleteIngradient(ingradientId);
	}
}