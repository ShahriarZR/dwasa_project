package com.example.DWASA_Backend.wastage;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WastageRepository extends JpaRepository<Wastage, Long> {
	Optional<Wastage> findByIdAndDeletedAtIsNull(Long id);

	List<Wastage> findAllByDeletedAtIsNullOrderByIdDesc();
}