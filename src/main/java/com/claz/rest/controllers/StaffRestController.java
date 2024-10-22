package com.claz.rest.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.claz.models.Staff;
import com.claz.services.StaffService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/staffs")
public class StaffRestController {
	@Autowired
	StaffService staffService;

	BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

	@GetMapping()
	public List<Staff> findAll() {
		return staffService.findAll();
	}

	@PostMapping()
	public Staff create(@RequestBody Staff staff) {
		String rawPassword = staff.getPassword();
		String encodedPassword = pe.encode(rawPassword);
		staff.setPassword(encodedPassword);
		return staffService.create(staff);
	}

	@GetMapping("/{username}")
	public ResponseEntity<Optional<Staff>> findByUsername(@PathVariable String username) {
		Optional<Staff> account = staffService.findByUsername(username);
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{username}")
	public ResponseEntity<Staff> updateAccount(@PathVariable String username, @RequestBody Staff updatedAccount) {
		Staff existingAccount = staffService.GetUsername(username);
		if (existingAccount == null) {
			return ResponseEntity.notFound().build();
		}

		// Cập nhật thông tin tài khoản
		existingAccount.setFullname(updatedAccount.getFullname());
		existingAccount.setEmail(updatedAccount.getEmail());
		existingAccount.setPhone(updatedAccount.getPhone());
		existingAccount.setImage(updatedAccount.getImage());
		existingAccount.setGender(updatedAccount.isGender());
		existingAccount.setRole(updatedAccount.isRole());

		Staff savedAccount = staffService.create(existingAccount);
		return ResponseEntity.ok(savedAccount);
	}

	@DeleteMapping("{username}")
	public void delete(@PathVariable("username") String username) {
		staffService.delete(username);
	}
}
