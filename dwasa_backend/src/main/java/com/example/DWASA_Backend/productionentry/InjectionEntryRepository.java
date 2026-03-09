package com.example.DWASA_Backend.productionentry;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionEntryRepository extends JpaRepository<InjectionEntry, Long> {
	Optional<InjectionEntry> findByIdAndDeletedAtIsNull(Long id);

	List<InjectionEntry> findAllByDeletedAtIsNullOrderByIdDesc();
}

