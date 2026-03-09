package com.example.DWASA_Backend.section;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionIngradientUnitRepository extends JpaRepository<SectionIngradientUnit, Long> {
	List<SectionIngradientUnit> findAllBySectionIdOrderByIdAsc(Long sectionId);

	void deleteAllBySectionId(Long sectionId);
}