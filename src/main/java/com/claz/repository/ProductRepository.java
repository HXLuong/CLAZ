package com.claz.repository;

import com.claz.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryId(int Category_ID);
//    @Query("SELECT p FROM Product p WHERE p.Name LIKE CONCAT('%', :name, '%')")
//    List<Product> findByName(@Param("name") String name);
}
