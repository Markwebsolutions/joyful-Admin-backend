package com.joyful.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByNameIgnoreCase(String name);
	   Optional<Category> findByName(String name);
}
