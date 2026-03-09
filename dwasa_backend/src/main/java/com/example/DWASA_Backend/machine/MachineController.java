package com.example.DWASA_Backend.machine;

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
@RequestMapping(path = "/api/machines", produces = MediaType.APPLICATION_JSON_VALUE)
public class MachineController {
	private final MachineService machineService;

	public MachineController(MachineService machineService) {
		this.machineService = machineService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MachineResponse createMachine(@Valid @RequestBody CreateMachineRequest request) {
		return machineService.createMachine(request);
	}

	@GetMapping
	public List<MachineResponse> getAllMachines() {
		return machineService.getAllMachines();
	}

	@GetMapping(path = "/{machineId}")
	public MachineResponse getMachine(@PathVariable Long machineId) {
		return machineService.getMachine(machineId);
	}

	@PutMapping(path = "/{machineId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public MachineResponse updateMachine(
			@PathVariable Long machineId,
			@Valid @RequestBody UpdateMachineRequest request) {
		return machineService.updateMachine(machineId, request);
	}

	@DeleteMapping(path = "/{machineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMachine(@PathVariable Long machineId) {
		machineService.deleteMachine(machineId);
	}
}
