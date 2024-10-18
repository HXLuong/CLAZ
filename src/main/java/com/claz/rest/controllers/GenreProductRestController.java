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

import com.claz.models.GenreProduct;
import com.claz.services.GenreProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/genre_products")
public class GenreProductRestController {
	@Autowired
	GenreProductService gpro;

	@GetMapping()
	public List<GenreProduct> getAll() {
		return gpro.findAll();
	}

	@GetMapping("{id}")
	public Optional<GenreProduct> getOne(@PathVariable("id") int id) {
		return gpro.findById(id);
	}

	@PostMapping()
	public GenreProduct create(@RequestBody GenreProduct gp) {
		return gpro.create(gp);
	}

	@PutMapping("{id}")
	public GenreProduct update(@PathVariable("id") int id, @RequestBody GenreProduct gp) {
		return gpro.update(gp);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") int id) {
		gpro.delete(id);
	}
}
