package com.example.DWASA_Backend.ingradient;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngradientRepository extends JpaRepository<Ingradient, Long> {
	Optional<Ingradient> findByIdAndDeletedAtIsNull(Long id);

	List<Ingradient> findAllByDeletedAtIsNullOrderByIdDesc();
}