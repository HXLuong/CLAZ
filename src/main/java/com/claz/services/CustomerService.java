package com.claz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.claz.models.Customer;

@Service
public interface CustomerService {

	List<Customer> findAll();

	Customer findByUsername(String username);

	Optional<Customer> getname(String username);

	Optional<Customer> findByEmail(String email);

	Customer createAccount(Customer Customer);

	Customer update(Customer username);

	void delete(String username);
}
