package com.joyful.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Product;
import com.joyful.repository.ProductRepository;
import com.joyful.service.ImageStorageService;
import com.joyful.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageStorageService imageStorageService;

	@Override
	public Product addProduct(Product product) {
		processProductImages(product);
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product updateProduct(Long id, Product updatedProduct) {
		Product existing = getProductById(id);

		// Copy basic fields
		existing.setName(updatedProduct.getName());
		existing.setDescription(updatedProduct.getDescription());
		existing.setProducttags(updatedProduct.getProducttags());
		existing.setFilter(updatedProduct.getFilter());
		existing.setMetatitle(updatedProduct.getMetatitle());
		existing.setMetadescription(updatedProduct.getMetadescription());
		existing.setPagekeywords(updatedProduct.getPagekeywords());
		existing.setIspublished(updatedProduct.getIspublished());
		existing.setNewarrival(updatedProduct.getNewarrival());
		existing.setSubcategories(updatedProduct.getSubcategories());

		// Process and update images
		processProductImages(updatedProduct);
		existing.setMainimage(updatedProduct.getMainimage());
		existing.setHoverimage(updatedProduct.getHoverimage());
		existing.setVariantsMap(updatedProduct.getVariantsMap());

		return productRepository.save(existing);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	// 🔄 Central image processing logic
	private void processProductImages(Product product) {
		product.setMainimage(processImageIfPresent(product.getMainimage()));
		product.setHoverimage(processImageIfPresent(product.getHoverimage()));

		if (product.getVariantsMap() != null) {
			product.getVariantsMap().forEach((type, variantList) -> {
				variantList.forEach(variant -> {
					variant.setImage(processImageIfPresent(variant.getImage()));
				});
			});
		}
	}

	// 🧹 Clean utility method
	private String processImageIfPresent(String imagePath) {
		if (imagePath != null && !imagePath.isBlank()) {
			return imageStorageService.storeImage(imagePath);
		}
		return imagePath;
	}
}
