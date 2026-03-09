package com.example.DWASA_Backend.machine;

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
public class MachineService {
	private final MachineRepository machineRepository;
	private final UserRepository userRepository;

	public MachineService(MachineRepository machineRepository, UserRepository userRepository) {
		this.machineRepository = machineRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public MachineResponse createMachine(CreateMachineRequest request) {
		Long userId = resolveAuthenticatedUserId();

		Machine machine = new Machine();
		machine.setMachineName(request.getMachineName().trim());
		machine.setSection(trimToNull(request.getSection()));
		machine.setLocation(trimToNull(request.getLocation()));
		machine.setBrand(trimToNull(request.getBrand()));
		machine.setRemarks(trimToNull(request.getRemarks()));
		machine.setStatus(request.getStatus() == null ? MachineStatus.ACTIVE : request.getStatus());
		machine.setCreatedBy(userId);
		machine.setUpdatedBy(userId);

		Machine saved = machineRepository.save(machine);
		return new MachineResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<MachineResponse> getAllMachines() {
		return machineRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(MachineResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public MachineResponse getMachine(Long machineId) {
		Machine machine = machineRepository.findByIdAndDeletedAtIsNull(machineId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Machine not found"));
		return new MachineResponse(machine);
	}

	@Transactional
	public MachineResponse updateMachine(Long machineId, UpdateMachineRequest request) {
		Machine machine = machineRepository.findByIdAndDeletedAtIsNull(machineId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Machine not found"));

		if (request.getMachineName() != null) {
			String updatedName = request.getMachineName().trim();
			if (updatedName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MACHINE_NAME cannot be blank");
			}
			machine.setMachineName(updatedName);
		}

		if (request.getSection() != null) {
			machine.setSection(trimToNull(request.getSection()));
		}

		if (request.getLocation() != null) {
			machine.setLocation(trimToNull(request.getLocation()));
		}

		if (request.getBrand() != null) {
			machine.setBrand(trimToNull(request.getBrand()));
		}

		if (request.getRemarks() != null) {
			machine.setRemarks(trimToNull(request.getRemarks()));
		}

		if (request.getStatus() != null) {
			machine.setStatus(request.getStatus());
		}

		machine.setUpdatedBy(resolveAuthenticatedUserId());
		Machine saved = machineRepository.save(machine);
		return new MachineResponse(saved);
	}

	@Transactional
	public void deleteMachine(Long machineId) {
		Machine machine = machineRepository.findByIdAndDeletedAtIsNull(machineId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Machine not found"));

		machine.setDeletedAt(OffsetDateTime.now());
		machine.setStatus(MachineStatus.INACTIVE);
		machine.setUpdatedBy(resolveAuthenticatedUserId());
		machineRepository.save(machine);
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
