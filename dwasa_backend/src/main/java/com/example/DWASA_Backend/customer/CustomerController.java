package com.example.DWASA_Backend.customer;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
		return customerService.createCustomer(request);
	}

	@GetMapping
	public List<CustomerResponse> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@GetMapping(path = "/{customerId}")
	public CustomerResponse getCustomer(@PathVariable Long customerId) {
		return customerService.getCustomer(customerId);
	}

	@PutMapping(path = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public CustomerResponse updateCustomer(
			@PathVariable Long customerId,
			@Valid @RequestBody UpdateCustomerRequest request) {
		return customerService.updateCustomer(customerId, request);
	}

	@DeleteMapping(path = "/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable Long customerId) {
		customerService.deleteCustomer(customerId);
	}
}
