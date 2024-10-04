package com.claz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("/category")
	public String danhmuc(Model model) {
		return "/category/category";
	}

	@RequestMapping("/account")
	public String upaccount(Model model) {
		return "/update_profile/account_profile";
	}

	@RequestMapping("/password")
	public String uppass(Model model) {
		return "/update_profile/password_profile";
	}

	@RequestMapping("/payment")
	public String giaodich(Model model) {
		return "/update_profile/payment_profile";
	}

	@RequestMapping("/order")
	public String donhang(Model model) {
		return "/update_profile/order_profile";
	}

	@RequestMapping("/comment")
	public String binhluan(Model model) {
		return "/update_profile/comment_profile";
	}

	@RequestMapping("/favorite")
	public String sanphamyeuthich(Model model) {
		return "/update_profile/favorite_profile";
	}

	@RequestMapping("/introduct")
	public String gioithieu(Model model) {
		return "/update_profile/introduct_profile";
	}

	@RequestMapping("/instruct_createAccount")
	public String huongdantaotaikhoan(Model model) {
		return "/instruct/instruct_createAccount";
	}

	@RequestMapping("/cart_dashboard")
	public String huongdanquanlygiohang(Model model) {
		return "/dashboard/cart_dashboard";
	}

	@RequestMapping("/payment_dashboard")
	public String payment_dashboard(Model model) {
		return "/dashboard/payment_dashboard";
	}

	@RequestMapping("/vnpay_dashboard")
	public String vnpay_dashboard(Model model) {
		return "/dashboard/payment_vnpay_dashboard";
	}

	@RequestMapping("/momo_dashboard")
	public String momo_dashboard(Model model) {
		return "/dashboard/payment_momo_dashboard";
	}

	@RequestMapping("/account_dashboard")
	public String account_dashboard(Model model) {
		return "/dashboard/account_dashboard";
	}

	@RequestMapping("/buymain_dashboard")
	public String buymain_dashboard(Model model) {
		return "/dashboard/buymain_dashboard";
	}

	@RequestMapping("/bonus_dashboard")
	public String bonus_dashboard(Model model) {
		return "/dashboard/bonus_dashboard";
	}

	@RequestMapping("/about_dashboard")
	public String about_dashboard(Model model) {
		return "/dashboard/about_dashboard";
	}

	@RequestMapping("/security_dashboard")
	public String security_dashboard(Model model) {
		return "/dashboard/security_dashboard";
	}

	@RequestMapping("/signup_dashboard")
	public String signup_dashboard(Model model) {
		return "/dashboard/signup_dashboard2";
	}

	@RequestMapping("/detailProduct")
	public String detailProduct(Model model) {
		return "/detailProduct/detailProduct";
	}

	@RequestMapping("/detail_profile")
	public String detail_profile(Model model) {
		return "/update_profile/detail_profile";
	}

    public String login(Model model) {
        return "/login/login";
    }
	
	@RequestMapping("/cart-index")
    public String cart(Model model) {
        return "/cart/cart-index";
    }
}
