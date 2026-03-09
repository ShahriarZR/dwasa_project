package com.example.DWASA_Backend.machine;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
	Optional<Machine> findByIdAndDeletedAtIsNull(Long id);

	List<Machine> findAllByDeletedAtIsNullOrderByIdDesc();
}
