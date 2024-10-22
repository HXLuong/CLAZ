package com.claz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.GenreProduct;

@Repository
public interface GenreProductRepository extends JpaRepository<GenreProduct, Integer> {
	@Query("SELECT gp FROM GenreProduct gp WHERE gp.product.id = ?1")
	List<GenreProduct> findAllByProductId(int Product_ID);
}
