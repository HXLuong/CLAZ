package com.claz.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Product;
import com.claz.models.Staff;
import com.claz.services.OrderDetailService;
import com.claz.services.OrderService;
import com.claz.services.ProductService;
import com.claz.services.StaffService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class ManagerController {
	@Autowired
	StaffService staffService;

	@Autowired
	private OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	ProductService productService;

	@RequestMapping("/admin")
	public String adm(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/dashboard");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminCategory")
	public String adminCategory(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-category");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminGenre")
	public String adminGenre(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-genre");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminProduct")
	public String adminProduct(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-product");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminStaff")
	public String adminStaff(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-staff");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminCustomer")
	public String adminCustomer(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-customer");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListProduct")
	public String adminListProduct(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-listProduct");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminGalary")
	public String adminGalary(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-galary");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListStaff")
	public String adminListStaff(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-listStaff");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListCustomer")
	public String adminListCustomer(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-listCustomer");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListCategory")
	public String adminListCategory(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-listCategory");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListGenre")
	public String adminListGenre(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-listGenre");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListOrder")
	public String adminListOrder(Model model, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		nav(model, request);

		Pageable pageable = PageRequest.of(page, size);
		Page<Order> ordersPage = orderService.findAllOrdersSorted(pageable);

		List<Map<String, Object>> orderTotals = new ArrayList<>();
		for (Order order : ordersPage.getContent()) {
			List<OrderDetail> allOrderDetails = order.getOrderDetails();
			double totalAmount = 0.0;
			for (OrderDetail detail : allOrderDetails) {
				double lineTotal = Math.ceil(detail.getPrice() * (1 - detail.getDiscount() / 100) / 5000) * 5000;
				totalAmount += lineTotal * detail.getQuantity();
			}
			Map<String, Object> orderTotal = new HashMap<>();
			orderTotal.put("id", order.getId());
			orderTotal.put("status", order.getStatus());
			orderTotal.put("paymentMethod", order.getPaymentMethod());
			orderTotal.put("created_at", order.getCreated_at());
			orderTotal.put("customer", order.getCustomer());
			orderTotal.put("orderDetails", order.getOrderDetails());
			orderTotal.put("amount", totalAmount);
			orderTotals.add(orderTotal);
		}

		model.addAttribute("orders", orderTotals);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ordersPage.getTotalPages());
		model.addAttribute("page", "/admin/admin-listOrder");
		return "/admin/admin-index";
	}

	@GetMapping("/adminCancelOrder")
	public String CancelOrder(@RequestParam("orderId") int orderId, Model model, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		nav(model, request);
		Order order = orderService.findById(orderId);

		if (order != null) {
			order.setStatus("Đang xử lý");
			orderService.save(order);
		}
		Pageable pageable = PageRequest.of(page, size);
		Page<Order> ordersPage = orderService.findAllOrdersSorted(pageable);

		List<Map<String, Object>> orderTotals = new ArrayList<>();
		for (Order orders : ordersPage.getContent()) {
			List<OrderDetail> allOrderDetails = orders.getOrderDetails();
			double totalAmount = 0.0;
			for (OrderDetail detail : allOrderDetails) {
				double lineTotal = Math.ceil(detail.getPrice() * (1 - detail.getDiscount() / 100) / 5000) * 5000;
				totalAmount += lineTotal * detail.getQuantity();
			}
			Map<String, Object> orderTotal = new HashMap<>();
			orderTotal.put("id", orders.getId());
			orderTotal.put("status", orders.getStatus());
			orderTotal.put("paymentMethod", orders.getPaymentMethod());
			orderTotal.put("created_at", orders.getCreated_at());
			orderTotal.put("customer", orders.getCustomer());
			orderTotal.put("orderDetails", orders.getOrderDetails());
			orderTotal.put("amount", totalAmount);
			orderTotals.add(orderTotal);
		}

		model.addAttribute("orders", orderTotals);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ordersPage.getTotalPages());
		model.addAttribute("page", "/admin/admin-listOrder");

		return "/admin/admin-index";
	}

	@GetMapping("/adminUpdateOrder")
	public String updateOrderStatus(@RequestParam("orderId") int orderId, Model model, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		nav(model, request);
		Order order = orderService.findById(orderId);

		if (order != null) {
			List<OrderDetail> orderDetails = order.getOrderDetails();
			for (OrderDetail orderDetail : orderDetails) {
				Product product = orderDetail.getProduct();
				if (product != null) {
					product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
					productService.update(product);
				}
			}
			order.setStatus("Đã được hủy");
			order.setAmount(0.0);
			orderService.save(order);
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<Order> ordersPage = orderService.findAllOrdersSorted(pageable);

		List<Map<String, Object>> orderTotals = new ArrayList<>();
		for (Order orders : ordersPage.getContent()) {
			List<OrderDetail> allOrderDetails = orders.getOrderDetails();
			double totalAmount = 0.0;
			for (OrderDetail detail : allOrderDetails) {
				double lineTotal = Math.ceil(detail.getPrice() * (1 - detail.getDiscount() / 100) / 5000) * 5000;
				totalAmount += lineTotal * detail.getQuantity();
			}
			Map<String, Object> orderTotal = new HashMap<>();
			orderTotal.put("id", orders.getId());
			orderTotal.put("status", orders.getStatus());
			orderTotal.put("paymentMethod", orders.getPaymentMethod());
			orderTotal.put("created_at", orders.getCreated_at());
			orderTotal.put("customer", orders.getCustomer());
			orderTotal.put("orderDetails", orders.getOrderDetails());
			orderTotal.put("amount", totalAmount);
			orderTotals.add(orderTotal);
		}

		model.addAttribute("orders", orderTotals);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ordersPage.getTotalPages());
		model.addAttribute("page", "/admin/admin-listOrder");

		return "/admin/admin-index";
	}

	@GetMapping("/adminSearchOrder")
	public String searchOrders(Model model, @RequestParam(required = false) String keyword, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20000") int size) {
		nav(model, request);
		Pageable pageable = PageRequest.of(page, size);
		Page<Order> ordersPage = orderService.searchOrders(keyword, pageable);

		List<Map<String, Object>> orderTotals = new ArrayList<>();
		for (Order order : ordersPage.getContent()) {
			List<OrderDetail> allOrderDetails = order.getOrderDetails();
			double totalAmount = 0.0;
			for (OrderDetail detail : allOrderDetails) {
				double lineTotal = Math.ceil(detail.getPrice() * (1 - detail.getDiscount() / 100) / 5000) * 5000;
				totalAmount += lineTotal * detail.getQuantity();
			}
			Map<String, Object> orderTotal = new HashMap<>();
			orderTotal.put("id", order.getId());
			orderTotal.put("status", order.getStatus());
			orderTotal.put("paymentMethod", order.getPaymentMethod());
			orderTotal.put("created_at", order.getCreated_at());
			orderTotal.put("customer", order.getCustomer());
			orderTotal.put("orderDetails", order.getOrderDetails());
			orderTotal.put("amount", totalAmount);
			orderTotals.add(orderTotal);
		}

		model.addAttribute("orders", orderTotals);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ordersPage.getTotalPages());
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", "/admin/admin-listOrder");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminOrderDetail")
	public String adminOrderDetail(Model model, HttpServletRequest request, @RequestParam("id") int id) {
		nav(model, request);
		Order order = orderService.findById(id);
		List<OrderDetail> orderDetails = order.getOrderDetails();
		List<Map<String, Object>> roundedOrderDetails = new ArrayList<>();
		double totalAmount = 0.0;

		for (OrderDetail detail : orderDetails) {
			Map<String, Object> newDetail = new HashMap<>();
			newDetail.put("id", detail.getId());
			newDetail.put("product", detail.getProduct());
			newDetail.put("quantity", detail.getQuantity());
			newDetail.put("discount", detail.getDiscount());
			newDetail.put("order", detail.getOrder());
			
			double lineTotal = Math.ceil(detail.getPrice() * (1 - detail.getDiscount() / 100) / 5000) * 5000;
			newDetail.put("price", lineTotal);
			double roundedPrice = lineTotal * detail.getQuantity();

			newDetail.put("roundedPrice", roundedPrice);

			roundedOrderDetails.add(newDetail);
			totalAmount += roundedPrice;
		}

		model.addAttribute("roundedOrderDetails", roundedOrderDetails);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("order", order);
		model.addAttribute("page", "/admin/admin-orderDetail");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminSlide")
	public String adminSlide(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-slide");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminComment")
	public String adminComment(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-comment");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminUpdateAccount")
	public String adminUpdateAccount(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-updateAccount");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminUpdatePassword")
	public String adminUpdatePassword(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/admin-updatePassword");
		return "/admin/admin-index";
	}

	@RequestMapping("/statisticRevenueCustomer")
	public String statisticRevenueCustomer(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/statistics_RevenueCustomer");
		return "/admin/admin-index";
	}

	@RequestMapping("/statisticRevenueProduct")
	public String statisticRevenueProduct(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/statistics_RevenueProduct");
		return "/admin/admin-index";
	}

	@RequestMapping("/statisticRevenueTotalProduct")
	public String statisticRevenueTotalProduct(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/statistics_RevenueTotalProduct");
		return "/admin/admin-index";
	}

	public void nav(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		Staff staff = staffService.findByUsername(username).get();
		model.addAttribute("fullname", staff.getFullname());
		model.addAttribute("image", "/images/" + staff.getImage());
		model.addAttribute("role", staff.isRole());
	}
}
