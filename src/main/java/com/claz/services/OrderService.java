package com.claz.services;

import java.util.List;

import com.claz.models.Customer;
import com.claz.models.Order;

public interface OrderService {

	Order createOrder(int id, String status, String paymentMethod, Double amount, Customer customer);

	List<Order> findByCustomer(Customer customer);

	List<Order> findByUsername(String username);

	List<Object[]> findOrderByUsername(String username);

	Order findById(int id);

	List<Order> findAll();

	int totalOrder();
}