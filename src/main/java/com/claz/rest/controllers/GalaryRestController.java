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

import com.claz.models.Galary;
import com.claz.services.GalaryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/galaries")
public class GalaryRestController {
	@Autowired
	GalaryService gpro;

	@GetMapping()
	public List<Galary> getAll() {
		return gpro.findAll();
	}

	@GetMapping("{id}")
	public Optional<Galary> getOne(@PathVariable("id") int id) {
		return gpro.findById(id);
	}

	@PostMapping()
	public Galary create(@RequestBody Galary g) {
		g.setImage(g.getImage());
		return gpro.create(g);
	}

	@PutMapping("{id}")
	public Galary update(@PathVariable("id") int id, @RequestBody Galary g) {
		g.setImage(g.getImage());
		return gpro.update(g);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") int id) {
		gpro.delete(id);
	}
}