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
	
	@RequestMapping("/detailProduct")
    public String detailProduct(Model model) {
        return "/detailProduct/detailProduct";
    }
	
	@RequestMapping("/login")
    public String login(Model model) {
        return "/login/login";
    }
}
