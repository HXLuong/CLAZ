package com.claz.services;

import java.util.List;

import com.claz.models.Customer;
import com.claz.models.Order;

public interface OrderService {

//	Order createOrder(Customer customer);

	List<Order> findByCustomer(Customer customer);

	List<Order> findByUsername(String username);

	Order findById(int id);

	List<Order> findAll();

	int totalOrder();
}