package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Genre;
import com.claz.repositories.GenreRepository;
import com.claz.services.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public List<Genre> findAll() {
		return genreRepository.findAll();
	}

	@Override
	public Optional<Genre> findById(int id) {
		return genreRepository.findById(id);
	}

	@Override
	public Genre create(Genre genre) {
		return genreRepository.save(genre);
	}

	@Override
	public Genre update(Genre genre) {
		return genreRepository.save(genre);
	}

	@Override
	public void delete(int id) {
		genreRepository.deleteById(id);
	}
}
