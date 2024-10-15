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

import com.claz.models.Genre;
import com.claz.services.GenreService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/genres")
public class GenreRestController {
	@Autowired
	GenreService gen;

	@GetMapping()
	public List<Genre> getAll() {
		return gen.findAll();
	}

	@GetMapping("{id}")
	public Optional<Genre> getOne(@PathVariable("id") int id) {
		return gen.findById(id);
	}

	@PostMapping()
	public Genre create(@RequestBody Genre genre) {
		return gen.create(genre);
	}

	@PutMapping("{id}")
	public Genre update(@PathVariable("id") int id, @RequestBody Genre genre) {
		return gen.update(genre);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") int id) {
		gen.delete(id);
	}
}
