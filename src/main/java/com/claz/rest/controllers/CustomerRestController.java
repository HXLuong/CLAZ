package com.claz.rest.controllers;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@Autowired
	StaffService staffService;

	BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

	@GetMapping()
	public List<Customer> findAll() {
		return customerService.findAll();
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		// Kiểm tra email đã tồn tại
		Optional<Customer> existingEmail = customerService.findByEmail(customer.getEmail());
		if (existingEmail.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
		}

		Optional<Customer> existingUsername = customerService.getname(customer.getUsername());
		if (existingUsername.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username đã tồn tại");
		}

		Optional<Staff> existingEmailStaff = staffService.findByEmail(customer.getEmail());
		if (existingEmailStaff.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
		}

		Optional<Staff> existingUsernameStaff = staffService.getname(customer.getUsername());
		if (existingUsernameStaff.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username đã tồn tại");
		}

		String rawPassword = customer.getPassword();
		String encodedPassword = pe.encode(rawPassword);
		customer.setPassword(encodedPassword);
		Customer savedCustomer = customerService.createAccount(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
	}

	@GetMapping("/{username}")
	public ResponseEntity<Customer> findByUsername(@PathVariable String username) {
		Customer account = customerService.getname(username).get();
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> updateAccount(@PathVariable String username, @RequestBody Customer updatedAccount) {
		Customer existingAccount = customerService.getname(username).get();
		if (existingAccount == null) {
			return ResponseEntity.notFound().build();
		}

		if (!existingAccount.getEmail().equals(updatedAccount.getEmail())) {
			Optional<Customer> existingEmail = customerService.findByEmail(updatedAccount.getEmail());
			if (existingEmail.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
			}

			Optional<Staff> existingEmailStaff = staffService.findByEmail(updatedAccount.getEmail());
			if (existingEmailStaff.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
			}
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
