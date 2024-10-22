package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.claz.models.Product;
import com.claz.repositories.ProductRepository;
import com.claz.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

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
	public List<Product> findAllByCategoryId(int Category_ID) {
		return productRepository.findAllByCategoryId(Category_ID);
	}

	@Override
	public List<Product> findBySearch(String search) {
		return productRepository.findByContentContaining(search);
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

	@Override
	public List<Product> findByPrice(double minPrice, double maxPrice) {
		return productRepository.findByPrice(minPrice, maxPrice);
	}

	@Override
	public Page<Product> findbyDMandSort(int dm, Pageable pageable) {
		return productRepository.findbyDMandSort(dm, pageable);
	}

	@Override
	public Page<Product> findByPricePage(double minPrice, double maxPrice, Pageable pageable) {
		return productRepository.findByPricePage(minPrice, maxPrice, pageable);
	}

	@Override
	public List<Product> fillbyprice(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Optional<Product> findById(int id) {
		Optional<Product> product = productRepository.findById(id);
		return product;
	}

}
