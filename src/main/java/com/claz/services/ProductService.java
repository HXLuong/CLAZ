package com.claz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.claz.models.Product;

@Service
public interface ProductService {

	List<Product> findAll();

	Product finById(int id);

	List<Product> findAllByCategoryId(int Category_ID);

	List<Product> findBySearch(String search) ;

	Product create(Product product);

	Product update(Product product);

	void delete(int id);
}
