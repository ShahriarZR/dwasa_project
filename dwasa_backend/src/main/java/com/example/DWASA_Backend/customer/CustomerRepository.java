package com.example.DWASA_Backend.customer;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByIdAndDeletedAtIsNull(Long id);

	List<Customer> findAllByDeletedAtIsNullOrderByIdDesc();
}
