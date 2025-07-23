package com.joyful.service;

import java.util.*;
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
                category.setImagelink((String) row.get("category_imagelink"));
                category.setSeotitle((String) row.get("category_seotitle"));
                category.setSeokeywords((String) row.get("category_seokeywords"));
                category.setSeodescription((String) row.get("category_seodescription"));
                category.setPublished(Boolean.parseBoolean(row.get("category_ispublished").toString()));
                Category savedCategory = categoryRepository.save(category);

                // SUBCATEGORY
                String subcatName = (String) row.get("subcategory_name");
                Optional<Subcategory> existingSub = subcategoryRepository.findByNameAndCategory(subcatName, savedCategory);
                Subcategory subcategory = existingSub.orElseGet(() -> {
                    Subcategory newSub = new Subcategory();
                    newSub.setName(subcatName);
                    newSub.setCategories(List.of(savedCategory));
                    return newSub;
                });

                subcategory.setName(subcatName);
                subcategory.setImagepath((String) row.get("subcategory_imagepath"));
                subcategory.setMetatitle((String) row.get("subcategory_metatitle"));
                subcategory.setDescription((String) row.get("subcategory_description"));
                subcategory.setMetadescription((String) row.get("subcategory_metadescription"));
                subcategory.setSeokeywords((String) row.get("subcategory_seokeywords"));
                subcategory.setIspublished(Boolean.parseBoolean(row.get("subcategory_ispublished").toString()));
                subcategory = subcategoryRepository.save(subcategory);

                // PRODUCT
                Product product = new Product();
                product.setName((String) row.get("product_name"));
                product.setDescription((String) row.get("product_description"));
                product.setMainimage((String) row.get("product_mainimage"));
                product.setHoverimage((String) row.get("product_hoverimage"));

                String tags = (String) row.get("product_producttags");
                product.setProducttags(Arrays.asList(tags.split(",")));

                product.setFilter((String) row.get("product_filter"));
                product.setMetatitle((String) row.get("product_metatitle"));
                product.setMetadescription((String) row.get("product_metadescription"));
                product.setPagekeywords((String) row.get("product_pagekeywords"));
                product.setIspublished(Boolean.parseBoolean(row.get("product_ispublished").toString()));
                product.setNewarrival(Boolean.parseBoolean(row.get("product_newarrival").toString()));

                // Parse variantsMap JSON
                String variantsJson = (String) row.get("product_variantsMap");
                if (variantsJson != null && !variantsJson.trim().isEmpty()) {
                    try {
                        TypeReference<Map<String, List<Variant>>> typeRef = new TypeReference<>() {};
                        Map<String, List<Variant>> variantsMap = objectMapper.readValue(variantsJson, typeRef);
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
                failInfo.put("category_name", row.get("category_name") != null ? row.get("category_name").toString() : "-");
                failInfo.put("subcategory_name", row.get("subcategory_name") != null ? row.get("subcategory_name").toString() : "-");
                failInfo.put("product_name", row.get("product_name") != null ? row.get("product_name").toString() : "-");
                failInfo.put("error", e.getMessage());

                failedRows.add(failInfo);
            }

            index++;
        }

        return Map.of(
            "success", true,
            "message", successCount + " rows imported, " + failCount + " failed.",
            "failedRows", failedRows
        );
    }
}
