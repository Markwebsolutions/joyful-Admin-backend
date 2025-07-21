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

import com.joyful.entity.Subcategory;
import com.joyful.repository.SubcategoryRepository;
import com.joyful.service.SubcategoryService;

@RestController
@RequestMapping("/subcategories")
@CrossOrigin("*")
public class SubcategoryController {

	private final SubcategoryRepository subcategoryRepository;

	@Autowired
	private SubcategoryService subcategoryService;

	SubcategoryController(SubcategoryRepository subcategoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
	}

	@PostMapping
	public Subcategory addSubcategory(@RequestBody Subcategory subcategory) {
		return subcategoryService.addSubcategory(subcategory);
	}

	@PutMapping("/{id}")
	public Subcategory updateSubcategory(@PathVariable Long id, @RequestBody Subcategory subcategory) {
		return subcategoryService.updateSubcategory(id, subcategory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubcategory(@PathVariable Long id) {
		if (subcategoryService.hasProducts(id)) {
			return ResponseEntity.status(409)
					.body(java.util.Map.of("message", "This Sub-Category has products associated with it. Please delete product first."));
		}

		subcategoryService.deleteSubcategory(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public List<Subcategory> getAllSubcategories() {
		return subcategoryService.getAllSubcategories();
	}

	@GetMapping("/{id}")
	public Subcategory getSubcategoryById(@PathVariable Long id) {
		return subcategoryService.getSubcategoryById(id);
	}

	@GetMapping("/byCategory/{categoryId}")
	public List<Subcategory> getSubcategoriesByCategory(@PathVariable Long categoryId) {
		return subcategoryRepository.findByCategoryId(categoryId);
	}

}
