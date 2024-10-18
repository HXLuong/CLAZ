package com.claz.rest.controllers;

import java.util.List;

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

import com.claz.models.Product;
import com.claz.services.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	ProductService prod;

	@GetMapping()
	public List<Product> getAll() {
		return prod.findAll();
	}

	@GetMapping("{id}")
	public Product getOne(@PathVariable("id") int id) {
		return prod.finById(id);
	}

	@PostMapping()
	public Product create(@RequestBody Product product) {
		product.setImage(product.getImage());
		return prod.create(product);
	}

	@PutMapping("{id}")
	public Product update(@PathVariable("id") int id, @RequestBody Product product) {
		product.setImage(product.getImage());
		return prod.update(product);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") int id) {
		prod.delete(id);
	}
}
