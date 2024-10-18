package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Slide;
import com.claz.repositories.SlideRepository;
import com.claz.services.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

	@Autowired
	private SlideRepository slideRepository;

	@Override
	public List<Slide> findAll() {
		return slideRepository.findAll();
	}

	@Override
	public Optional<Slide> findById(int id) {
		return slideRepository.findById(id);
	}

	@Override
	public Slide create(Slide slide) {
		return slideRepository.save(slide);
	}

	@Override
	public Slide update(Slide slide) {
		return slideRepository.save(slide);
	}

	@Override
	public void delete(int id) {
		slideRepository.deleteById(id);
		;
	}
}
