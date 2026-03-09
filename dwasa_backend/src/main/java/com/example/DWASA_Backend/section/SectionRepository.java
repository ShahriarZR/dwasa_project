package com.example.DWASA_Backend.section;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
	Optional<Section> findByIdAndDeletedAtIsNull(Long id);

	List<Section> findAllByDeletedAtIsNullOrderByIdDesc();
}