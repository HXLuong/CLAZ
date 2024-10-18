package com.claz.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.claz.models.Product;

@Service
public interface ProductService {

List<Product> findAll();
	
	Page<Product> findAll(Pageable pageable);

	Product finById(int id);

	List<Product> findAllByCategoryId(int Category_ID);

	List<Product> findBySearch(String search) ;

	Product create(Product product);

	Product update(Product product);

	void delete(int id);
	
	List<Product> findByPrice(double minPrice, double maxPrice);
	
	Page<Product> findbyDMandSort(int dm,Pageable pageable);
	
	Page<Product> findByPricePage(double minPrice, double maxPrice,Pageable pageable);
	
	List<Product> fillbyprice(Sort sort);
}
