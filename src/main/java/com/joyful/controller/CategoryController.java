package com.joyful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyful.entity.Category;
import com.joyful.service.CategoryService;


@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category);
	}

	@PutMapping("/{id}")
	public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
		return categoryService.updateCategory(id, category);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		if (categoryService.hasSubcategories(id)) {
			return ResponseEntity.status(409)
					.body(java.util.Map.of("message", "This category has subcategories associated with it. Please delete Subcategory first."));
		}

		categoryService.deleteCategory(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable Long id) {
		return categoryService.getCategoryById(id);
	}

	@GetMapping("/by-name/{name}")
	public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
		return categoryService.getCategoryByName(name).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}