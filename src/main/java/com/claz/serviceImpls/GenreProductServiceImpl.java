package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.GenreProduct;
import com.claz.repositories.GenreProductRepository;
import com.claz.services.GenreProductService;

@Service
public class GenreProductServiceImpl implements GenreProductService {
	@Autowired
	private GenreProductRepository genreProductRepository;

	@Override
	public List<GenreProduct> findAll() {
		return genreProductRepository.findAll();
	}

	@Override
	public Optional<GenreProduct> findById(int id) {
		return genreProductRepository.findById(id);
	}

	@Override
	public GenreProduct create(GenreProduct gp) {
		return genreProductRepository.save(gp);
	}

	@Override
	public GenreProduct update(GenreProduct gp) {
		return genreProductRepository.save(gp);
	}

	@Override
	public void delete(int id) {
		genreProductRepository.deleteById(id);
	}

	@Override
	public List<GenreProduct> findAllByproductId(int Product_ID) {
		return genreProductRepository.findAllByProductId(Product_ID);
	}
}
