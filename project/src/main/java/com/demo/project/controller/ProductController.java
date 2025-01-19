package com.demo.project.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.project.entity.Product;
import com.demo.project.service.ProductNotFoundException;
import com.demo.project.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/product")
	public String create(@RequestBody Product product) {
		productService.create(product);
		return "Successfully Posted Data..";
	}

	@GetMapping("/product")
	public CompletableFuture<List<Product>> getAll() {
		return productService.findAll();
	}

	@GetMapping("/product/{id}")
	public CompletableFuture<Product> getById(@PathVariable int id) {
		return productService.getById(id);
	}

	@GetMapping("/products/{productName}")
	public CompletableFuture<List<Product>> getByProductName(@PathVariable String productName) {
		return productService.findByProductName(productName);
	}

	@GetMapping("/{price}")
	public CompletableFuture<List<Product>> getByPrice(@PathVariable int price) {
		// return productService.findByPrice(price);

		return productService.findByPrice(price).thenApply(products -> {
			if (products.isEmpty()) {
				throw new ProductNotFoundException("No products found with price: " + price);

			}

			return products;

		});

	}

	@PutMapping("/product/{id}")
	public String update(@PathVariable int id, @RequestBody Product product) {
		productService.update(product);
		return "updated Data Successfully";
	}

	@DeleteMapping("/product/{id}")
	public String delete(@PathVariable int id) {
		productService.delete(id);
		return "Successfully  Data.. is Deleted";
	}

}
