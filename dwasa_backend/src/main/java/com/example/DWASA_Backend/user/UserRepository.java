package com.example.DWASA_Backend.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmpId(String empId);

	Optional<User> findByEmail(String email);
}
