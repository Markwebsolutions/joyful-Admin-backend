package com.joyful.service;

import java.util.List;

import com.joyful.entity.Subcategory;

public interface SubcategoryService {
	Subcategory addSubcategory(Subcategory subcategory);

	Subcategory updateSubcategory(Long id, Subcategory updatedSubcategory);

	void deleteSubcategory(Long id);

	Subcategory getSubcategoryById(Long id);

	List<Subcategory> getAllSubcategories();

	List<Subcategory> getSubcategoriesByCategory(Long categoryId);

	boolean hasProducts(Long subcategoryId);

}
