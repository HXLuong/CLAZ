package com.claz.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.claz.models.Staff;
import com.claz.services.OrderDetailService;
import com.claz.services.OrderService;
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
	public String adminListOrder(Model model, HttpServletRequest request) {
		nav(model, request);
		List<Order> orders = orderService.findAll();
		model.addAttribute("orders", orders);
		model.addAttribute("page", "/admin/admin-listOrder");
		return "/admin/admin-index";
	}

	@GetMapping("/updateOrder")
	public String updateOrderStatus(@RequestParam("orderId") int orderId, Model model, HttpServletRequest request) {
		nav(model, request);
		Order order = orderService.findById(orderId);

		if (order != null) {
			order.setStatus("Đã được hủy");
			order.setAmount(0.0);
			orderService.save(order);
		}
		List<Order> orders = orderService.findAll();
		model.addAttribute("orders", orders);
		model.addAttribute("page", "/admin/admin-listOrder");

		return "/admin/admin-index";
	}

	@GetMapping("/searchOrder")
	public String searchOrders(Model model, @RequestParam(required = false) String keyword,
			HttpServletRequest request) {
		nav(model, request);
		List<Order> orders = orderService.searchOrders(keyword);
		model.addAttribute("orders", orders);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", "/admin/admin-listOrder");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminOrderDetail")
	public String adminOrderDetail(Model model, HttpServletRequest request, @RequestParam("id") int id) {
		nav(model, request);
		model.addAttribute("order", orderService.findById(id));
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

	@RequestMapping("/statisticRevenue")
	public String statisticRevenue(Model model, HttpServletRequest request) {
		nav(model, request);
		model.addAttribute("page", "/admin/statistics_Revenue");
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
