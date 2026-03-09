package com.example.DWASA_Backend.production;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<Production, Long> {
	Optional<Production> findByIdAndDeletedAtIsNull(Long id);

	List<Production> findAllByDeletedAtIsNullOrderByIdDesc();
}
