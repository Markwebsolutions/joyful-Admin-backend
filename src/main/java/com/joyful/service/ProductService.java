
package com.joyful.service;

import java.util.List;

import com.joyful.entity.Product;

public interface ProductService {

	Product addProduct(Product product);

	Product getProductById(Long id);

	List<Product> getAllProducts();

	Product updateProduct(Long id, Product product);

	void deleteProduct(Long id);
}
