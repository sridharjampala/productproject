package com.demo.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.project.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByProductName(String productName);

	List<Product> findByPrice(int price);

}
