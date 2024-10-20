package com.claz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Galary;

public interface GalaryRepository extends JpaRepository<Galary, Integer> {
	@Query("SELECT g FROM Galary g WHERE g.product.id = ?1")
	List<Galary> findAllByProductId(int Product_ID);
}
