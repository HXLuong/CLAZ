package com.claz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.claz.models.Customer;

@Service
public interface CustomerService {

	List<Customer> findAll();

	Customer findByUsername(String username);

	Optional<Customer> getname(String username);

	Customer createAccount(Customer Customer);

	Customer update(Customer username);

	void delete(String username);

}
