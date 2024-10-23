package com.claz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.claz.models.Staff;

@Controller
public class ManagerController {

	@RequestMapping("/admin")
	public String adm(Model model) {
		Staff staff = null;
		model.addAttribute("page", "/admin/dashboard");
		model.addAttribute("image", staff.getImage());
		return "/admin/admin-index";
	}

	@RequestMapping("/adminCategory")
	public String adminCategory(Model model) {
		model.addAttribute("page", "/admin/admin-category");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminGenre")
	public String adminGenre(Model model) {
		model.addAttribute("page", "/admin/admin-genre");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminProduct")
	public String adminProduct(Model model) {
		model.addAttribute("page", "/admin/admin-product");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminStaff")
	public String adminStaff(Model model) {
		model.addAttribute("page", "/admin/admin-staff");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminCustomer")
	public String adminCustomer(Model model) {
		model.addAttribute("page", "/admin/admin-customer");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListProduct")
	public String adminListProduct(Model model) {
		model.addAttribute("page", "/admin/admin-listProduct");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminGalary")
	public String adminGalary(Model model) {
		model.addAttribute("page", "/admin/admin-galary");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListStaff")
	public String adminListStaff(Model model) {
		model.addAttribute("page", "/admin/admin-listStaff");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListCustomer")
	public String adminListCustomer(Model model) {
		model.addAttribute("page", "/admin/admin-listCustomer");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListCategory")
	public String adminListCategory(Model model) {
		model.addAttribute("page", "/admin/admin-listCategory");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListGenre")
	public String adminListGenre(Model model) {
		model.addAttribute("page", "/admin/admin-listGenre");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminListOrder")
	public String adminListOrder(Model model) {
		model.addAttribute("page", "/admin/admin-listOrder");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminOrderDetail")
	public String adminOrderDetail(Model model) {
		model.addAttribute("page", "/admin/admin-orderDetail");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminSlide")
	public String adminSlide(Model model) {
		model.addAttribute("page", "/admin/admin-slide");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminComment")
	public String adminComment(Model model) {
		model.addAttribute("page", "/admin/admin-comment");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminUpdateAccount")
	public String adminUpdateAccount(Model model) {
		model.addAttribute("page", "/admin/admin-updateAccount");
		return "/admin/admin-index";
	}

	@RequestMapping("/adminUpdatePassword")
	public String adminUpdatePassword(Model model) {
		model.addAttribute("page", "/admin/admin-updatePassword");
		return "/admin/admin-index";
	}
}
