package com.example.DWASA_Backend.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductType, Long> {
	Optional<ProductType> findByIdAndDeletedAtIsNull(Long id);

	Optional<ProductType> findByProductCodeAndDeletedAtIsNull(String productCode);

	List<ProductType> findAllByDeletedAtIsNullOrderByIdDesc();
}