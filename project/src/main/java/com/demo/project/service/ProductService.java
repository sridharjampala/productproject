package com.demo.project.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.demo.project.entity.Product;
import com.demo.project.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	 @Retryable(
		        retryFor = { Exception.class },
		        maxAttempts = 3,
		        backoff = @Backoff(delay = 2000)
		    )
	public void create(Product product)
	{
		productRepository.save(product);
	}
	
	@Async
	@Cacheable(value = "product")
	 @Retryable(
		        retryFor = { Exception.class },
		        maxAttempts = 3,
		        backoff = @Backoff(delay = 2000)
		    )
	public CompletableFuture<List<Product>> findAll()
	{
		return CompletableFuture.completedFuture(productRepository.findAll());
	}
	
	@Async
	@Cacheable(value = "product", key = "#id")
	 @Retryable(
		        retryFor = { Exception.class },
		        maxAttempts = 3,
		        backoff = @Backoff(delay = 2000)
		    )
	public CompletableFuture<Product> getById(int id)
	{
		return CompletableFuture.completedFuture(productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("No Data Found")));
	}
	
	public String update(Product product)
	{
		productRepository.save(product);
		return "Updated Successfully..!";
	}
	
	@Caching(
			evict = {@CacheEvict(value = "product", allEntries = true),@CacheEvict(value = "product", key = "id")
			})
	public String delete(int id)
	{
		productRepository.deleteById(id);
		return "Deleted Successfully";
	}
	
	@Async
	@Cacheable(value = "product", key = "#productName")
	public CompletableFuture<List<Product>> findByProductName(String productName)
	{
		
		 List<Product> products = productRepository.findByProductName(productName);
		    
		    if (products.isEmpty()) {
		        throw new ProductNotFoundException("No products found with name: " + productName);
		    }
		    
		    return CompletableFuture.completedFuture(products);
	}
	
	@Async
	@Cacheable(value = "product", key = "#price")
	public CompletableFuture<List<Product>> findByPrice(int price)
	{
		 List<Product> products = productRepository.findByPrice(price);
		    
		    if (products.isEmpty()) {
		        throw new ProductNotFoundException("No products found with name: " + price);
		    }
		    
		    return CompletableFuture.completedFuture(products);
	}

}
