package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Product;
import com.claz.repositories.ProductRepository;
import com.claz.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
    private ProductRepository productRepository;
	
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product finById(int id) {
		Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
	}

	@Override
	public List<Product> findByCategoryId(int cid) {
		 return List.of();
	}

	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(int id) {
		productRepository.deleteById(id);
	}

}
