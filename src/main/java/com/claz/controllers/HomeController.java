package com.claz.controllers;

import com.claz.models.Product;
import com.claz.serviceImpls.ProductServiceImpl;
import com.claz.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

	@RequestMapping("/cart-index")
	public String cart(Model model) {
		model.addAttribute("page", "/cart/cart-index");
		return "index";
	}


	@RequestMapping("/account")
	public String upaccount(Model model) {
		model.addAttribute("page", "/update_profile/account_profile");
		return "index";
	}

	@RequestMapping("/password")
	public String uppass(Model model) {
		model.addAttribute("page", "/update_profile/password_profile");
		return "index";
	}

	@RequestMapping("/payment")
	public String giaodich(Model model) {
		model.addAttribute("page", "/update_profile/payment_profile");
		return "index";
	}

	@RequestMapping("/order")
	public String donhang(Model model) {
		model.addAttribute("page", "/update_profile/order_profile");
		return "index";
	}

	@RequestMapping("/comment")
	public String binhluan(Model model) {
		model.addAttribute("page", "/update_profile/comment_profile");
		return "index";
	}

	@RequestMapping("/favorite")
	public String sanphamyeuthich(Model model) {
		model.addAttribute("page", "/update_profile/favorite_profile");
		return "index";
	}

	@RequestMapping("/introduct")
	public String gioithieu(Model model) {
		model.addAttribute("page", "/update_profile/introduct_profile");
		return "index";
	}

	@RequestMapping("/detailProduct")
	public String detailProduct(Model model) {
		model.addAttribute("page", "/detailProduct/detailProduct");
		return "index";
	}

	@RequestMapping("/detail_profile")
	public String detail_profile(Model model) {
		model.addAttribute("page", "/update_profile/detail_profile");
		return "index";
	}

	public String login(Model model) {
		return "/login/login";
	}

//	@RequestMapping("/instruct_createAccount")
//	public String huongdantaotaikhoan(Model model) {
//		return "/instruct/instruct_createAccount";
//	}

	@RequestMapping("/cart_instruct")
	public String cart_instruct(Model model) {
		return "/instruct/cart_instruct";
	}

	@RequestMapping("/payment_instruct")
	public String payment_instruct(Model model) {
		return "/instruct/payment_instruct";
	}

	@RequestMapping("/vnpay_instruct")
	public String vnpay_instruct(Model model) {
		return "/instruct/payment_vnpay_instruct";
	}

	@RequestMapping("/momo_instruct")
	public String momo_instruct(Model model) {
		return "/instruct/payment_momo_instruct";
	}

	@RequestMapping("/account_instruct")
	public String account_instruct(Model model) {
		return "/instruct/account_instruct";
	}

	@RequestMapping("/buymain_instruct")
	public String buymain_instruct(Model model) {
		return "/instruct/buymain_instruct";
	}

	@RequestMapping("/bonus_instruct")
	public String bonus_instruct(Model model) {
		return "/instruct/bonus_instruct";
	}

	@RequestMapping("/about_instruct")
	public String about_instruct(Model model) {
		return "/instruct/about_instruct";
	}

	@RequestMapping("/security_instruct")
	public String security_instruct(Model model) {
		return "/instruct/security_instruct";
	}

	@RequestMapping("/signup_instruct")
	public String signup_instruct(Model model) {
		return "/instruct/signup_instruct";
	}
}
