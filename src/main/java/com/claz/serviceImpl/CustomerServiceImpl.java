package com.claz.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.claz.DAO.CustomerDAO;
import com.claz.models.Customer;
import com.claz.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDAO customerDAO;

	public List<Customer> findAll() {
		return customerDAO.findAll();
	}

	public Customer createAccount(Customer account) {
		return customerDAO.save(account);
	}

	@Override
	public Customer update(Customer customer) {
		return customerDAO.save(customer);
	}

	@Override
	public void delete(String id) {
		customerDAO.deleteById(id);
	}

	@Override
	public Customer findByUsername(String username) {
		return customerDAO.findById(username).get();
	}

	public Optional<Customer> getname(String username) {
		return customerDAO.findByUsername(username);
	}

}