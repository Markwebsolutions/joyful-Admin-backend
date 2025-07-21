package com.joyful.service;

import java.util.List;
import java.util.Optional;

import com.joyful.entity.Category;

public interface CategoryService {
	Category addCategory(Category category);

	Category updateCategory(Long id, Category updatedCategory);

	void deleteCategory(Long id);

	Category getCategoryById(Long id);

	List<Category> getAllCategories();

	Optional<Category> getCategoryByName(String name);

	boolean hasSubcategories(Long id);


}
