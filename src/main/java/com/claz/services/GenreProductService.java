package com.claz.services;

import java.util.List;
import java.util.Optional;

import com.claz.models.GenreProduct;

public interface GenreProductService {

	List<GenreProduct> findAll();

	Optional<GenreProduct> findById(int id);

	List<GenreProduct> findAllByproductId(int Product_ID);

	GenreProduct create(GenreProduct gp);

	GenreProduct update(GenreProduct gp);

	void delete(int id);
}
