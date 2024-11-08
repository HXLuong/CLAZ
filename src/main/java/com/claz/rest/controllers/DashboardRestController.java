package com.claz.rest.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Category;
import com.claz.models.Order;
import com.claz.services.CategoryService;
import com.claz.services.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {
	@Autowired
	OrderService orderService;

	@GetMapping("/orders-today")
	public long getOrdersToday() {
		return orderService.getOrdersToday();
	}

	@GetMapping("/revenue-today")
	public double getRevenueToday() {
		return orderService.getRevenueToday();
	}

	@GetMapping("/inventory-count")
	public long getInventoryCount() {
		return orderService.getInventoryCount();
	}

	@GetMapping("/total-customers")
	public long getTotalCustomers() {
		return orderService.getTotalCustomers();
	}

	@GetMapping("/customers/order-count")
	public List<Map<String, Object>> getAllCustomersWithOrderCount() {
		return orderService.getAllCustomersWithOrderCount();
	}
}
