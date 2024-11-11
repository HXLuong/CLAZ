package com.claz.serviceImpls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Category;
import com.claz.models.Customer;
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CustomerRepository;
import com.claz.repositories.OrderDetailRepository;
import com.claz.repositories.OrderRepository;
import com.claz.repositories.ProductRepository;
import com.claz.services.CategoryService;
import com.claz.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Order> findByCustomer(Customer customer) {
		return orderRepository.findByCustomer(customer);
	}

	@Override
	public List<Order> findByUsername(String username) {
		return orderRepository.findByUsername(username);
	}

	@Override
	public Order findById(int id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.orElse(null);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public int totalOrder() {
		int total = 0;
		List<Order> orders = orderRepository.findAll();
		for (Order order : orders) {
			total++;
		}
		return total;
	}

	@Override
	public Order createOrder(int id, String status, String paymentMethod, Double amount, Customer customer) {
		Order order = new Order();
		LocalDateTime now = LocalDateTime.now();

		order.setId(id);
		order.setStatus(status);
		order.setPaymentMethod(paymentMethod);
		order.setAmount(amount);
		order.setCustomer(customer);
		order.setCreated_at(now);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> filterOrders(Integer orderId, Double amountFrom, Double amountTo, LocalDateTime fromDate,
			LocalDateTime toDate, String username) {
		return orderRepository.filterOrders(orderId, amountFrom, amountTo, fromDate, toDate, username);
	}

	@Override
	public List<Order> filterPayments(String paymentMethod, Double amountFrom, Double amountTo, LocalDateTime fromDate,
			LocalDateTime toDate, String username) {
		return orderRepository.filterPayments(paymentMethod, amountFrom, amountTo, fromDate, toDate, username);
	}

	@Override
	public int getOrdersToday() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
		return orderRepository.countByCreatedAtAfter(startOfDay);
	}

	@Override
	public double getRevenueToday() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();

		List<Order> orders = orderRepository.findByCreatedAtAfter(startOfDay);
		double revenue = 0.0;

		for (Order order : orders) {
			for (OrderDetail detail : order.getOrderDetails()) {
				double orderPrice = detail.getPrice() * detail.getQuantity();
				revenue += orderPrice - (orderPrice * (detail.getDiscount() / 100));
			}
		}
		return revenue;
	}

	@Override
	public int getInventoryCount() {
		return productRepository.getTotalProductQuantity();
	}

	@Override
	public long getTotalCustomers() {
		return customerRepository.count();
	}

	@Override
	public List<Map<String, Object>> getAllCustomersWithOrderCount() {
		List<Object[]> results = orderRepository.findAllCustomersWithOrderCount();

		List<Map<String, Object>> customerOrderCounts = new ArrayList<>();

		for (Object[] result : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", result[0]);
			map.put("orderCount", result[1]);
			customerOrderCounts.add(map);
		}

		return customerOrderCounts;
	}

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Order> searchOrders(String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return orderRepository.findAll();
		}
		return orderRepository.searchOrders(keyword);
	}

}
