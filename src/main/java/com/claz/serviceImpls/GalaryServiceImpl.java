package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Galary;
import com.claz.repositories.GalaryRepository;
import com.claz.services.GalaryService;


@Service
public class GalaryServiceImpl implements GalaryService {

	@Autowired
	private GalaryRepository galaryRepository;

	@Override
	public List<Galary> findAll() {
		return galaryRepository.findAll();
	}

	@Override
	public Optional<Galary> findById(int id) {
		return galaryRepository.findById(id);
	}

	@Override
	public Galary create(Galary g) {
		return galaryRepository.save(g);
	}

	@Override
	public Galary update(Galary g) {
		return galaryRepository.save(g);
	}

	@Override
	public void delete(int id) {
		galaryRepository.deleteById(id);
	}

	@Override
	public List<Galary> findAllByproductId(int Product_ID) {
		return galaryRepository.findAllByProductId(Product_ID);
	}
}
