package com.joyful.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joyful.entity.Category;
import com.joyful.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	@Query("SELECT s FROM Subcategory s JOIN s.categories c WHERE c.id = :categoryId")
	List<Subcategory> findByCategoryId(@Param("categoryId") Long categoryId);

	@Query("SELECT COUNT(s) > 0 FROM Subcategory s JOIN s.categories c WHERE c.id = :categoryId")
	boolean existsByCategoryId(@Param("categoryId") Long categoryId);

	Optional<Subcategory> findByNameAndCategoriesContaining(String name, Category category);

	@Query("SELECT s FROM Subcategory s JOIN s.categories c WHERE s.name = :name AND c = :category")
	Optional<Subcategory> findByNameAndCategory(@Param("name") String name, @Param("category") Category category);

}
