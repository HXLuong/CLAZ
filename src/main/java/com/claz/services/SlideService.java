package com.claz.services;

import java.util.List;
import java.util.Optional;

import com.claz.models.Slide;

public interface SlideService {

	List<Slide> findAll();

	Optional<Slide> findById(int id);

	Slide create(Slide slide);

	Slide update(Slide slide);

	void delete(int id);
}