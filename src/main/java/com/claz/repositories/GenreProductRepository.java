package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claz.models.GenreProduct;

@Repository
public interface GenreProductRepository extends JpaRepository<GenreProduct, Integer> {
}
