package com.claz.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Map;

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

	Optional<Product> findById(int id);

	List<Product> findAllByCategoryId(int Category_ID);

	List<Product> findBySearch(String search);

	Product create(Product product);

	Product update(Product product);

	void delete(int id);

	Map<String, Double> getRevenuePerMonth();

	long countTotalProduct();

	Map<String, Double> getRevenueByDateRange(LocalDate startDate, LocalDate endDate);

	List<Product> findByPrice(double minPrice, double maxPrice);

	Page<Product> findbyDMandSort(int dm, Pageable pageable);

	Page<Product> findByPricePage(double minPrice, double maxPrice, Pageable pageable);

	List<Product> fillbyprice(Sort sort);
	
	List<Product> findByHot();

	List<Product> findByBestSeller(int purchases);
	
	List<Product> findProducts(Integer categoryId, Integer genreId, Double minPrice, Double maxPrice, String sort);
	
	List<Product> findAllByGenreId(int Genre_ID);
}
