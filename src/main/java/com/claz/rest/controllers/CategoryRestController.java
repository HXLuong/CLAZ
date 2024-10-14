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
import com.claz.services.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryService cate;

	@GetMapping()
	public List<Category> getAll() {
		return cate.findAll();
	}

	@GetMapping("{id}")
	public Optional<Category> getOne(@PathVariable("id") int id) {
		return cate.findById(id);
	}

	@PostMapping()
	public Category create(@RequestBody Category category) {
		return cate.create(category);
	}

	@PutMapping("{id}")
	public Category update(@PathVariable("id") int id, @RequestBody Category category) {
		return cate.update(category);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") int id) {
		cate.delete(id);
	}
}
