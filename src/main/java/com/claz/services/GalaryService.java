package com.claz.services;

import java.util.List;
import java.util.Optional;

import com.claz.models.Galary;

public interface GalaryService {

	List<Galary> findAll();

	Optional<Galary> findById(int id);

	List<Galary> findAllByproductId(int Product_ID);

	Galary create(Galary g);

	Galary update(Galary g);

	void delete(int id);
}