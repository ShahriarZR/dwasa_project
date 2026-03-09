package com.example.DWASA_Backend.unit;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
	Optional<Unit> findByIdAndDeletedAtIsNull(Long id);

	List<Unit> findAllByDeletedAtIsNullOrderByIdDesc();
}