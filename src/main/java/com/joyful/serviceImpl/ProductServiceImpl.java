package com.joyful.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Product;
import com.joyful.repository.ProductRepository;
import com.joyful.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) {
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

		existing.setName(updatedProduct.getName());
		existing.setDescription(updatedProduct.getDescription());
		existing.setMainimage(updatedProduct.getMainimage());
		existing.setHoverimage(updatedProduct.getHoverimage());
		existing.setProducttags(updatedProduct.getProducttags());
		existing.setFilter(updatedProduct.getFilter());
		existing.setMetatitle(updatedProduct.getMetatitle());
		existing.setMetadescription(updatedProduct.getMetadescription());
		existing.setPagekeywords(updatedProduct.getPagekeywords());
		existing.setIspublished(updatedProduct.getIspublished());
		existing.setSubcategories(updatedProduct.getSubcategories());
		existing.setVariantsMap(updatedProduct.getVariantsMap());
		existing.setNewarrival(updatedProduct.getNewarrival());
		return productRepository.save(existing);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
