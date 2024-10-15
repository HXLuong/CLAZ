package com.claz.repositories;

import com.claz.models.Product;

import java.util.List;

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


}
