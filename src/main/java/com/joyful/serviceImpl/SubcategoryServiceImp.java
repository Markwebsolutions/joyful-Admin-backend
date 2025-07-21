package com.joyful.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Category;
import com.joyful.entity.Subcategory;
import com.joyful.repository.CategoryRepository;
import com.joyful.repository.SubcategoryRepository;
import com.joyful.service.SubcategoryService;

@Service
public class SubcategoryServiceImp implements SubcategoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepo;

	@Autowired
	private CategoryRepository categoryRepo;

//	@Override
//	public Subcategory addSubcategory(Subcategory subcategory) {
//		// ✅ Fetch valid categories from DB by ID
//		List<Category> validCategories = subcategory.getCategories().stream()
//				.map(cat -> categoryRepo.findById(cat.getId())
//						.orElseThrow(() -> new RuntimeException("Category not found: " + cat.getId())))
//				.collect(Collectors.toList());
//
//		subcategory.setCategories(validCategories);
//
//		return subcategoryRepo.save(subcategory);
//	}
//	newly added today
	@Override
	public Subcategory addSubcategory(Subcategory subcategory) {
		// If frontend sent only categoryIds, convert them to full Category entities
		if (subcategory.getCategoryIds() != null && !subcategory.getCategoryIds().isEmpty()) {
			List<Category> validCategories = subcategory.getCategoryIds().stream()
					.map(catId -> categoryRepo.findById(catId)
							.orElseThrow(() -> new RuntimeException("Category not found: " + catId)))
					.collect(Collectors.toList());
			subcategory.setCategories(validCategories);
		}

		return subcategoryRepo.save(subcategory);
	}

	@Override
	public Subcategory updateSubcategory(Long id, Subcategory updatedSubcategory) {
		try {
			Subcategory sub = subcategoryRepo.findById(id)
					.orElseThrow(() -> new RuntimeException("Subcategory not found"));

			sub.setName(updatedSubcategory.getName());
			sub.setDescription(updatedSubcategory.getDescription());
			sub.setImagepath(updatedSubcategory.getImagepath());
			sub.setMetatitle(updatedSubcategory.getMetatitle());
			sub.setMetadescription(updatedSubcategory.getMetadescription());
			sub.setSeokeywords(updatedSubcategory.getSeokeywords());
			sub.setIspublished(updatedSubcategory.isIspublished());

			// ✅ Update category list
			List<Category> validCategories = updatedSubcategory.getCategories().stream()
					.map(cat -> categoryRepo.findById(cat.getId())
							.orElseThrow(() -> new RuntimeException("Category not found: " + cat.getId())))
					.collect(Collectors.toList());

			sub.setCategories(validCategories);

			return subcategoryRepo.save(sub);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update subcategory: " + e.getMessage());
		}
	}

	@Override
	public void deleteSubcategory(Long id) {
		subcategoryRepo.deleteById(id);
	}

	@Override
	public Subcategory getSubcategoryById(Long id) {
		return subcategoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found"));
	}

	@Override
	public List<Subcategory> getAllSubcategories() {
		return subcategoryRepo.findAll();
	}

	@Override
	public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
		return subcategoryRepo.findByCategoryId(categoryId);
	}

	@Override
	public boolean hasProducts(Long subcategoryId) {
		Subcategory sub = subcategoryRepo.findById(subcategoryId)
				.orElseThrow(() -> new RuntimeException("Subcategory not found"));
		return sub.getProducts() != null && !sub.getProducts().isEmpty();
	}

}
