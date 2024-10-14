package com.claz.controllers;

import com.claz.models.Product;
import com.claz.services.CategoryService;
import com.claz.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> pr = productService.getAllProduct().stream().filter(e -> e.getCategory().getId() == 1).toList();
        model.addAttribute("products", pr);
        List<Product> pr2 = productService.getAllProduct().stream().filter(e -> e.getCategory().getId() == 4).toList();
        model.addAttribute("gamesteam", pr2);
        List<Product> pr3 = productService.getAllProduct().stream().filter(e -> e.getCategory().getId() == 2).toList();
        model.addAttribute("lamviec", pr3);
        List<Product> pr4 = productService.getAllProduct().stream().filter(e -> e.getCategory().getId() == 3).toList();
        model.addAttribute("hoctap", pr3);
        model.addAttribute("page", "component/home");
        return "index";
    }

    @RequestMapping("/cart-index")
    public String cart(Model model) {
        model.addAttribute("page", "/cart/cart-index");
        return "index";
    }

    @RequestMapping("/category")
    public String danhmuc(Model model) {
        model.addAttribute("page", "/category/category");
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
