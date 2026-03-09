package com.example.DWASA_Backend.customer;

import com.example.DWASA_Backend.user.User;
import com.example.DWASA_Backend.user.UserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final UserRepository userRepository;

	public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public CustomerResponse createCustomer(CreateCustomerRequest request) {
		Long userId = resolveAuthenticatedUserId();

		Customer customer = new Customer();
		customer.setName(request.getName().trim());
		customer.setEmail(trimToNull(request.getEmail()));
		customer.setPhone(trimToNull(request.getPhone()));
		customer.setType(request.getType());
		customer.setContactPerson(trimToNull(request.getContactPerson()));
		customer.setRemarks(trimToNull(request.getRemarks()));
		customer.setStatus(request.getStatus() == null ? CustomerStatus.ACTIVE : request.getStatus());
		customer.setAddress(trimToNull(request.getAddress()));
		customer.setShopLocation(trimToNull(request.getShopLocation()));
		customer.setCreatedBy(userId);
		customer.setUpdatedBy(userId);

		Customer saved = customerRepository.save(customer);
		return new CustomerResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<CustomerResponse> getAllCustomers() {
		return customerRepository.findAllByDeletedAtIsNullOrderByIdDesc()
				.stream()
				.map(CustomerResponse::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public CustomerResponse getCustomer(Long customerId) {
		Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
		return new CustomerResponse(customer);
	}

	@Transactional
	public CustomerResponse updateCustomer(Long customerId, UpdateCustomerRequest request) {
		Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		if (request.getName() != null) {
			String updatedName = request.getName().trim();
			if (updatedName.isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NAME cannot be blank");
			}
			customer.setName(updatedName);
		}

		if (request.getEmail() != null) {
			customer.setEmail(trimToNull(request.getEmail()));
		}

		if (request.getPhone() != null) {
			customer.setPhone(trimToNull(request.getPhone()));
		}

		if (request.getType() != null) {
			customer.setType(request.getType());
		}

		if (request.getContactPerson() != null) {
			customer.setContactPerson(trimToNull(request.getContactPerson()));
		}

		if (request.getRemarks() != null) {
			customer.setRemarks(trimToNull(request.getRemarks()));
		}

		if (request.getStatus() != null) {
			customer.setStatus(request.getStatus());
		}

		if (request.getAddress() != null) {
			customer.setAddress(trimToNull(request.getAddress()));
		}

		if (request.getShopLocation() != null) {
			customer.setShopLocation(trimToNull(request.getShopLocation()));
		}

		customer.setUpdatedBy(resolveAuthenticatedUserId());
		Customer saved = customerRepository.save(customer);
		return new CustomerResponse(saved);
	}

	@Transactional
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		customer.setDeletedAt(OffsetDateTime.now());
		customer.setStatus(CustomerStatus.INACTIVE);
		customer.setUpdatedBy(resolveAuthenticatedUserId());
		customerRepository.save(customer);
	}

	private Long resolveAuthenticatedUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authentication");
		}

		String empId = String.valueOf(authentication.getPrincipal());
		return userRepository.findByEmpId(empId)
				.map(User::getId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
