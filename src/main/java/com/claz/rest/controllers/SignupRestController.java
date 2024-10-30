package com.claz.rest.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Customer;
import com.claz.models.Staff;
import com.claz.services.CustomerService;
import com.claz.services.StaffService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/customer")
public class SignupRestController {
	@Autowired
	CustomerService customerService;

	@Autowired
	StaffService staffService;

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
	public ResponseEntity<?> create(@RequestBody Customer customer) {
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

		customer.setImage("profile.jpg");
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
		Customer savedCustomer = customerService.createAccount(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
	}

	@PutMapping("/current")
	public ResponseEntity<?> updateUser(@RequestBody Customer customer) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Customer> existingCustomerOptional = customerService.getname(currentUsername);

		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();

			if (!existingCustomer.getEmail().equals(customer.getEmail())) {
				Optional<Customer> existingEmail = customerService.findByEmail(customer.getEmail());
				if (existingEmail.isPresent()) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
				}

				Optional<Staff> existingEmailStaff = staffService.findByEmail(customer.getEmail());
				if (existingEmailStaff.isPresent()) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại");
				}
			}

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
