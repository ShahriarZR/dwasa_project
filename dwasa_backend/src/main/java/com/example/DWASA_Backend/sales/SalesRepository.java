package com.example.DWASA_Backend.sales;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
	Optional<Sales> findByIdAndDeletedAtIsNull(Long id);

	List<Sales> findAllByDeletedAtIsNullOrderByIdDesc();
}
