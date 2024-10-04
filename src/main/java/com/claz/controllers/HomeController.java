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
