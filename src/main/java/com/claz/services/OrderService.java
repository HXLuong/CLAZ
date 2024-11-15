package com.claz.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claz.models.Customer;
import com.claz.models.Order;

public interface OrderService {

	Order save(Order order);

	Order createOrder(int id, String status, String paymentMethod, Double amount, Customer customer);

	List<Order> findByCustomer(Customer customer);

	List<Order> findByUsername(String username);

	Order findById(int id);

	List<Order> findAll();
	
	Page<Order> findAllOrdersSorted(Pageable pageable);

	int totalOrder();

	List<Order> filterOrders(Integer orderId, Double amountFrom, Double amountTo, LocalDateTime fromDate,
			LocalDateTime toDate, String username);

	List<Order> filterPayments(String paymentMethod, Double amountFrom, Double amountTo, LocalDateTime fromDate,
			LocalDateTime toDate, String username);

	int getOrdersToday();

	double getRevenueToday();

	int getInventoryCount();

	long getTotalCustomers();

	List<Map<String, Object>> getAllCustomersWithOrderCount();

	Page<Order> searchOrders(String keyword, Pageable pageable);
}