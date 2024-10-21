package com.claz.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.claz.models.Product;

@Service
public interface ProductService {

	List<Product> findAll();

	Product finById(int id);

	List<Product> findAllByCategoryId(int Category_ID);

	List<Product> findBySearch(String search);

	Product create(Product product);

	Product update(Product product);

	void delete(int id);

	Map<String, Double> getRevenuePerMonth();

	long countTotalProduct();

	Map<String, Double> getRevenueByDateRange(LocalDate startDate, LocalDate endDate);

}
