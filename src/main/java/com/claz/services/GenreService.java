package com.claz.services;

import java.util.List;
import java.util.Optional;

import com.claz.models.Genre;

public interface GenreService {

	List<Genre> findAll();

	Optional<Genre> findById(int id);

	Genre create(Genre category);

	Genre update(Genre category);

	void delete(int id);
}
