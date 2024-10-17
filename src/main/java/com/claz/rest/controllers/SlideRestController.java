package com.claz.rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Category;
import com.claz.models.Slide;
import com.claz.services.CategoryService;
import com.claz.services.SlideService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/slides")
public class SlideRestController {
	@Autowired
	SlideService sli;

	@GetMapping()
	public List<Slide> getAll() {
		return sli.findAll();
	}

	@GetMapping("{id}")
	public Optional<Slide> getOne(@PathVariable("id") int id) {
		return sli.findById(id);
	}

	@PostMapping()
	public Slide create(@RequestBody Slide slide) {
		slide.setImage(slide.getImage());
		return sli.create(slide);
	}

	@PutMapping("{id}")
	public Slide update(@PathVariable("id") int id, @RequestBody Slide slide) {
		slide.setImage(slide.getImage());
		return sli.update(slide);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") int id) {
		sli.delete(id);
	}
}
