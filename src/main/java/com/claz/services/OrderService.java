package com.claz.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.claz.models.Customer;
import com.claz.models.Order;

public interface OrderService {

	Order createOrder(int id, String status, String paymentMethod, Double amount, Customer customer);

	List<Order> findByCustomer(Customer customer);

	List<Order> findByUsername(String username);

	Order findById(int id);

	List<Order> findAll();

	int totalOrder();

<<<<<<< HEAD

=======
	List<Order> filterOrders(Integer orderId, Double amountFrom, Double amountTo, LocalDateTime fromDate,
			LocalDateTime toDate, String username);

	List<Order> filterPayments(String paymentMethod, Double amountFrom, Double amountTo, LocalDateTime fromDate,
			LocalDateTime toDate, String username);

	int getOrdersToday();

	double getRevenueToday();

	int getInventoryCount();

	long getTotalCustomers();

	List<Map<String, Object>> getAllCustomersWithOrderCount();
>>>>>>> abb2f9bd68225994a1d661327f725013cc17c6ac
}