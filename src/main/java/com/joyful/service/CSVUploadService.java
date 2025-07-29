package com.joyful.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyful.entity.Category;
import com.joyful.entity.Product;
import com.joyful.entity.Subcategory;
import com.joyful.entity.Variant;
import com.joyful.repository.CategoryRepository;
import com.joyful.repository.ProductRepository;
import com.joyful.repository.SubcategoryRepository;

@Service
public class CSVUploadService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ImageStorageService imageStorageService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public Map<String, Object> processCSVData(List<Map<String, Object>> csvRows) {
		int successCount = 0;
		int failCount = 0;
		List<Map<String, Object>> failedRows = new ArrayList<>();

		int index = 0;
		for (Map<String, Object> row : csvRows) {
			if (row.values().stream().allMatch(val -> val == null || val.toString().trim().isEmpty())) {
				index++;
				continue;
			}

			try {
				// CATEGORY
				String categoryName = (String) row.get("category_name");
				Category category = categoryRepository.findByName(categoryName).orElseGet(Category::new);
				category.setName(categoryName);
				category.setDescription((String) row.get("category_description"));
				category.setSearchkeywords((String) row.get("category_searchkeywords"));
				category.setImagelink(processImageUrl((String) row.get("category_imagelink")));
				category.setSeotitle((String) row.get("category_seotitle"));
				category.setSeokeywords((String) row.get("category_seokeywords"));
				category.setSeodescription((String) row.get("category_seodescription"));
				category.setPublished(Boolean.parseBoolean(row.get("category_ispublished").toString()));
				Category savedCategory = categoryRepository.save(category); // ✅ final reference for lambda

				// SUBCATEGORY
				String subcatName = (String) row.get("subcategory_name");
				Optional<Subcategory> existingSub = subcategoryRepository.findByNameAndCategory(subcatName,
						savedCategory);
				Subcategory subcategory = existingSub.orElseGet(() -> {
					Subcategory newSub = new Subcategory();
					newSub.setName(subcatName);
					newSub.setCategories(List.of(savedCategory));
					return newSub;
				});

				subcategory.setImagepath(processImageUrl((String) row.get("subcategory_imagepath")));
				subcategory.setMetatitle((String) row.get("subcategory_metatitle"));
				subcategory.setDescription((String) row.get("subcategory_description"));
				subcategory.setMetadescription((String) row.get("subcategory_metadescription"));
				subcategory.setSeokeywords((String) row.get("subcategory_seokeywords"));
				subcategory.setIspublished(Boolean.parseBoolean(row.get("subcategory_ispublished").toString()));
				subcategory = subcategoryRepository.save(subcategory);

				// PRODUCT
				String productName = (String) row.get("product_name");
				Optional<Product> existingProduct = productRepository.findByNameAndSubcategoriesContaining(productName,
						subcategory);
				Product product = existingProduct.orElseGet(Product::new);

				product.setName(productName);
				product.setDescription((String) row.get("product_description"));
				product.setMainimage(processImageUrl((String) row.get("product_mainimage")));
				product.setHoverimage(processImageUrl((String) row.get("product_hoverimage")));

				String tags = (String) row.get("product_producttags");
				product.setProducttags(Arrays.asList(tags.split(",")));

				product.setFilter((String) row.get("product_filter"));
				product.setMetatitle((String) row.get("product_metatitle"));
				product.setMetadescription((String) row.get("product_metadescription"));
				product.setPagekeywords((String) row.get("product_pagekeywords"));
				product.setIspublished(Boolean.parseBoolean(row.get("product_ispublished").toString()));
				product.setNewarrival(Boolean.parseBoolean(row.get("product_newarrival").toString()));

				// VARIANTS
				String variantsJson = (String) row.get("product_variantsMap");
				if (variantsJson != null && !variantsJson.trim().isEmpty()) {
					try {
						variantsJson = variantsJson.replaceAll("[\\n\\r]+", "").replaceAll("\\\\\"", "\"");
						TypeReference<Map<String, List<Variant>>> typeRef = new TypeReference<>() {
						};
						Map<String, List<Variant>> variantsMap = objectMapper.readValue(variantsJson, typeRef);

						for (List<Variant> variantList : variantsMap.values()) {
							for (Variant variant : variantList) {
								variant.setImage(processImageUrl(variant.getImage()));
							}
						}

						product.setVariantsMap(variantsMap);
					} catch (Exception e) {
						product.setVariantsMap(null);
						System.err.println("❌ Failed to parse variantsMap: " + variantsJson);
						e.printStackTrace();
					}
				}

				product.setSubcategories(Set.of(subcategory));
				productRepository.save(product);

				successCount++;
			} catch (Exception e) {
				failCount++;
				Map<String, Object> failInfo = new HashMap<>();
				failInfo.put("rowIndex", index);
				failInfo.put("product_name", row.get("product_name"));
				failInfo.put("error", e.getMessage());
				failedRows.add(failInfo);
			}

			index++;
		}

		return Map.of("success", true, "message", successCount + " rows imported, " + failCount + " failed.",
				"failedRows", failedRows);
	}

	private String processImageUrl(String imageUrl) {
		if (imageUrl == null || imageUrl.isBlank())
			return null;
		try {
			return imageStorageService.storeImage(imageUrl);
		} catch (Exception e) {
			System.err.println("⚠️ Failed to process image: " + imageUrl + " -> " + e.getMessage());
			return imageUrl;
		}
	}
}
