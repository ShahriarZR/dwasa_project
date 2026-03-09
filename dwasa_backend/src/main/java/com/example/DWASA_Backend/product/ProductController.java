package com.example.DWASA_Backend.product;

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
@RequestMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request) {
		return productService.createProduct(request);
	}

	@GetMapping
	public List<ProductResponse> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping(path = "/{productId}")
	public ProductResponse getProduct(@PathVariable Long productId) {
		return productService.getProduct(productId);
	}

	@PutMapping(path = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ProductResponse updateProduct(
			@PathVariable Long productId,
			@Valid @RequestBody UpdateProductRequest request) {
		return productService.updateProduct(productId, request);
	}

	@DeleteMapping(path = "/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
	}
}