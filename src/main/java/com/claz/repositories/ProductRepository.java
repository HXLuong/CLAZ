package com.claz.repositories;

import com.claz.models.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> findAllByCategoryId(int Category_ID);

	@Query("SELECT p FROM Product p WHERE p.Name LIKE %:querySearch%")
	List<Product> findByContentContaining(@Param("querySearch") String querySearch);

	@Query("SELECT p FROM Product p WHERE p.Price BETWEEN ?1 AND ?2")
	List<Product> findByPrice(double minPrice, double maxPrice);
	
	@Query("SELECT o FROM Product o where o.category.id=?1")
	Page<Product> findbyDMandSort(int dm,Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.Price BETWEEN ?1 AND ?2")
	Page<Product> findByPricePage(double minPrice, double maxPrice,Pageable pageable);
	
	List<Product> findAll(Sort sort);


}
