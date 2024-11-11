package com.claz.rest.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.claz.repositories.OrderDetailRepository;
import com.claz.services.CustomerService;
import com.claz.services.ProductService;

@CrossOrigin
@RestController
public class BurnChartRestController {

	@Autowired
	ProductService productService;

	@Autowired
	CustomerService customerService;

	@GetMapping("/api/revenue")
	public Map<String, Double> getRevenuePerMonth() {
		return productService.getRevenuePerMonth();
	}

	@GetMapping("/api/revenue/total")
	public Double getTotalRevenue() {
		Map<String, Double> revenueMap = productService.getRevenuePerMonth();
		return revenueMap.values().stream().mapToDouble(Double::doubleValue).sum();
	}

	@GetMapping("/api/customer/total")
	public long getTotalUsers() {
		return customerService.countTotalCustomers();
	}

	@GetMapping("/api/product/total")
	public long getTotalProduct() {
		return productService.countTotalProduct();
	}

	@GetMapping("/api/revenue/filter")
	public Map<String, Double> getRevenueByDateRange(@RequestParam("start") String start,
			@RequestParam("end") String end) {
		try {
			LocalDate startDate = LocalDate.parse(start);
			LocalDate endDate = LocalDate.parse(end);
			return productService.getRevenueByDateRange(startDate, endDate);
		} catch (DateTimeParseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ngày tháng không hợp lệ", e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Có lỗi xảy ra trong server", e);
		}
	}

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@GetMapping("/api")
	public ResponseEntity<?> getDailyRevenueByYear(@RequestParam("year") int year) {
		List<Map<String, Object>> revenueData = orderDetailRepository.findDailyRevenueByYear(year);
		return ResponseEntity.ok(revenueData);
	}
}
