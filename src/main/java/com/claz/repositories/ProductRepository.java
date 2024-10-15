package com.claz.repositories;

import com.claz.models.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
<<<<<<< HEAD:src/main/java/com/claz/repository/ProductRepository.java
import org.springframework.data.repository.query.Param;
=======
>>>>>>> cefbaa9380fbe81f6f5454181f197dfe67734ff9:src/main/java/com/claz/repositories/ProductRepository.java
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
<<<<<<< HEAD:src/main/java/com/claz/repository/ProductRepository.java
    List<Product> findAllByCategoryId(int Category_ID);
//    @Query("SELECT p FROM Product p WHERE p.Name LIKE CONCAT('%', :name, '%')")
//    List<Product> findByName(@Param("name") String name);
=======

	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> findByCategoryId(int cid);
>>>>>>> cefbaa9380fbe81f6f5454181f197dfe67734ff9:src/main/java/com/claz/repositories/ProductRepository.java
}
