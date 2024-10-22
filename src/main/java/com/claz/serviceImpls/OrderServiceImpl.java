package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Category;
import com.claz.models.Customer;
import com.claz.models.Order;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.OrderRepository;
import com.claz.services.CategoryService;
import com.claz.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

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

}
