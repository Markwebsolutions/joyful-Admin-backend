package com.joyful.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.Product;
import com.joyful.entity.Subcategory;

public interface ProductRepository extends JpaRepository<Product, Long> {
	// Custom query methods can be added here if needed later
	Optional<Product> findByNameAndSubcategoriesContaining(String name, Subcategory subcategory);
}
