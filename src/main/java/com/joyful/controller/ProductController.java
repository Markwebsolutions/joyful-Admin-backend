
package com.joyful.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyful.entity.Product;
import com.joyful.repository.ProductRepository;
import com.joyful.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

//	newly added code
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Long id) {
		Product p = productRepository.findById(id).orElseThrow();
		// 🧠 preload categories inside each subcategory
		p.getSubcategories().forEach(s -> s.getCategories().size());
		return p;
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		try {
			System.out.println("🟢 Creating product with variantsMap: " + product.getVariantsMap());
			return productService.addProduct(product);
		} catch (Exception e) {
			System.err.println("❌ Error during product creation: " + e.getMessage());
			throw new RuntimeException("Invalid product data: " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
		try {
			System.out.println("🟢 Updating product with variantsMap: " + product.getVariantsMap());
			return productService.updateProduct(id, product);
		} catch (Exception e) {
			System.err.println("❌ Error during product update: " + e.getMessage());
			throw new RuntimeException("Invalid update data: " + e.getMessage());
		}
	}

}
