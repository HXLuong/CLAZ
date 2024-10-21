package com.claz.rest.controllers;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Customer;
import com.claz.models.LoginRequest;
import com.claz.models.Staff;
import com.claz.services.CustomerService;
import com.claz.services.StaffService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/customers")
public class CustomerRestController {
	@Autowired
	CustomerService customerService;

	BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

	@GetMapping()
	public List<Customer> findAll() {
		return customerService.findAll();
	}

	@PostMapping()
	public Customer create(@RequestBody Customer customer) {
		String rawPassword = customer.getPassword();
		String encodedPassword = pe.encode(rawPassword);
		customer.setPassword(encodedPassword);
		return customerService.createAccount(customer);
	}

	@GetMapping("/{username}")
	public ResponseEntity<Customer> findByUsername(@PathVariable String username) {
		Customer account = customerService.getname(username).get();
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{username}")
	public ResponseEntity<Customer> updateAccount(@PathVariable String username, @RequestBody Customer updatedAccount) {
		Customer existingAccount = customerService.getname(username).get();
		if (existingAccount == null) {
			return ResponseEntity.notFound().build();
		}

		existingAccount.setFullname(updatedAccount.getFullname());
		existingAccount.setEmail(updatedAccount.getEmail());
		existingAccount.setPhone(updatedAccount.getPhone());
		existingAccount.setImage(updatedAccount.getImage());
		existingAccount.setGender(updatedAccount.isGender());

		Customer savedAccount = customerService.createAccount(existingAccount);
		return ResponseEntity.ok(savedAccount);
	}

	@DeleteMapping("{username}")
	public void delete(@PathVariable("username") String username) {
		customerService.delete(username);
	}

}
