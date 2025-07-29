package com.joyful.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Category;
import com.joyful.entity.Subcategory;
import com.joyful.repository.CategoryRepository;
import com.joyful.repository.SubcategoryRepository;
import com.joyful.service.CategoryService;
import com.joyful.service.ImageStorageService;

@Service
public class CategoryServiceImp implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private SubcategoryRepository subcategoryRepository;

	@Autowired
	private ImageStorageService imageStorageService;

	@Override
	public Category addCategory(Category category) {
		// Process image before saving
		String processedImage = imageStorageService.storeImage(category.getImagelink());
		category.setImagelink(processedImage);
		return categoryRepo.save(category);
	}

	@Override
	public Category updateCategory(Long id, Category updatedCategory) {
		Category cat = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

		cat.setName(updatedCategory.getName());
		cat.setDescription(updatedCategory.getDescription());
		cat.setSearchkeywords(updatedCategory.getSearchkeywords());
		cat.setSeotitle(updatedCategory.getSeotitle());

		// Download + FTP upload image, then store final URL
		String processedImage = imageStorageService.storeImage(updatedCategory.getImagelink());
		cat.setImagelink(processedImage);

		cat.setSeokeywords(updatedCategory.getSeokeywords());
		cat.setSeodescription(updatedCategory.getSeodescription());
		cat.setPublished(updatedCategory.getPublished());

		return categoryRepo.save(cat);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public Category getCategoryById(Long id) {
		Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

		if (category.getSubcategories() != null) {
			for (Subcategory subcategory : category.getSubcategories()) {
				subcategory.setProducts(subcategory.getProducts()); // force-load products
			}
		}
		return category;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();

		for (Category category : categories) {
			if (category.getSubcategories() != null) {
				for (Subcategory subcategory : category.getSubcategories()) {
					subcategory.setProducts(subcategory.getProducts());
				}
			}
		}
		return categories;
	}

	@Override
	public Optional<Category> getCategoryByName(String name) {
		return categoryRepo.findByNameIgnoreCase(name);
	}

	@Override
	public boolean hasSubcategories(Long id) {
		return subcategoryRepository.existsByCategoryId(id);
	}
}
