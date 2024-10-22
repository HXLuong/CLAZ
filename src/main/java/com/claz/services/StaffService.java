package com.claz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.claz.models.Customer;
import com.claz.models.Staff;

@Service
public interface StaffService {

	List<Staff> findAll();

	Optional<Staff> findByUsername(String username);

	Staff GetUsername(String username);

	Staff create(Staff staff);

	Staff update(Staff staff);

	void delete(String username);

	Optional<Staff> getname(String username);

}
