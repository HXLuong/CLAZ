package com.claz.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Customer;
import com.claz.services.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/customer")
public class SignupController {
	@Autowired
	CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/current")
	public ResponseEntity<Customer> getCurrentUser() {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Customer> customerOptional = customerService.getname(currentUsername);

		if (customerOptional.isPresent()) {
			return ResponseEntity.ok(customerOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping
	public Customer create(@RequestBody Customer customer) {
		customer.setImage("profile.jpg");
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
		return customerService.createAccount(customer);
	}

	@PutMapping("/current")
	public ResponseEntity<Customer> updateUser(@RequestBody Customer customer) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Customer> existingCustomerOptional = customerService.getname(currentUsername);

		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();
			existingCustomer.setEmail(customer.getEmail());
			existingCustomer.setFullname(customer.getFullname());
			existingCustomer.setPhone(customer.getPhone());
			existingCustomer.setGender(customer.isGender());
			existingCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));

			Customer updatedCustomer = customerService.update(existingCustomer);
			return ResponseEntity.ok(updatedCustomer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/currentimg")
	public ResponseEntity<Customer> updateUserImg(@RequestBody Customer customer) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Customer> existingCustomerOptional = customerService.getname(currentUsername);

		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();
			existingCustomer.setImage(customer.getImage());
			Customer updatedCustomer = customerService.update(existingCustomer);
			return ResponseEntity.ok(updatedCustomer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

//	@PostMapping
//	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
//		customer.setImage("user.png");
//		String encodedPassword = passwordEncoder.encode(customer.getPassword());
//		customer.setPassword(encodedPassword);
//		Customer savedCustomer = customerService.createAccount(customer);
//		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
//	}

}
