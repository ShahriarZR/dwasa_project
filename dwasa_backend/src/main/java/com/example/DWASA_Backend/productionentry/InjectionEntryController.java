package com.example.DWASA_Backend.productionentry;

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
@RequestMapping(path = "/api/injection-entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class InjectionEntryController {
	private final InjectionEntryService injectionEntryService;

	public InjectionEntryController(InjectionEntryService injectionEntryService) {
		this.injectionEntryService = injectionEntryService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public InjectionEntryResponse createInjectionEntry(@Valid @RequestBody CreateInjectionEntryRequest request) {
		return injectionEntryService.createInjectionEntry(request);
	}

	@GetMapping
	public List<InjectionEntryResponse> getAllInjectionEntries() {
		return injectionEntryService.getAllInjectionEntries();
	}

	@GetMapping(path = "/{injectionEntryId}")
	public InjectionEntryResponse getInjectionEntry(@PathVariable Long injectionEntryId) {
		return injectionEntryService.getInjectionEntry(injectionEntryId);
	}

	@PutMapping(path = "/{injectionEntryId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public InjectionEntryResponse updateInjectionEntry(
			@PathVariable Long injectionEntryId,
			@Valid @RequestBody UpdateInjectionEntryRequest request) {
		return injectionEntryService.updateInjectionEntry(injectionEntryId, request);
	}

	@DeleteMapping(path = "/{injectionEntryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteInjectionEntry(@PathVariable Long injectionEntryId) {
		injectionEntryService.deleteInjectionEntry(injectionEntryId);
	}

	@GetMapping(path = "/sections/{sectionId}/template-items")
	public List<SectionInjectionTemplateItemResponse> getSectionTemplateItems(@PathVariable Long sectionId) {
		return injectionEntryService.getSectionTemplate(sectionId);
	}
}

