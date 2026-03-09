package com.example.DWASA_Backend.section;

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
@RequestMapping(path = "/api/sections", produces = MediaType.APPLICATION_JSON_VALUE)
public class SectionController {
	private final SectionService sectionService;

	public SectionController(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public SectionResponse createSection(@Valid @RequestBody CreateSectionRequest request) {
		return sectionService.createSection(request);
	}

	@GetMapping
	public List<SectionResponse> getAllSections() {
		return sectionService.getAllSections();
	}

	@GetMapping(path = "/{sectionId}")
	public SectionResponse getSection(@PathVariable Long sectionId) {
		return sectionService.getSection(sectionId);
	}

	@PutMapping(path = "/{sectionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public SectionResponse updateSection(
			@PathVariable Long sectionId,
			@Valid @RequestBody UpdateSectionRequest request) {
		return sectionService.updateSection(sectionId, request);
	}

	@DeleteMapping(path = "/{sectionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSection(@PathVariable Long sectionId) {
		sectionService.deleteSection(sectionId);
	}
}