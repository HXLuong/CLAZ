package com.claz.controllers;

import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.models.Slide;
import com.claz.repositories.CategoryRepository;
import com.claz.services.CategoryService;
import com.claz.services.ProductService;
import com.claz.services.SlideService;

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
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SlideService slideService;

	@RequestMapping("/")
	public String index(HttpSession session, Model model) {
		List<Product> pr = productService.findAll().stream().filter(e -> e.getCategory().getId() == 1).toList();
		session.setAttribute("products", pr);
		List<Product> pr2 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 4).toList();
		session.setAttribute("gamesteam", pr2);
		List<Product> pr3 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 2).toList();
		session.setAttribute("lamviec", pr3);
		List<Product> pr4 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 3).toList();
		session.setAttribute("hoctap", pr4);

		List<Category> cate = categoryService.findAll();
		model.addAttribute("cates", cate);

		List<Slide> slide = slideService.findAll();
		session.setAttribute("slides", slide);

		session.setAttribute("page", "component/home");
		return "index";
	}

	@PostMapping("/searchProduct")
	public String searchProduct(@RequestParam(name = "search", required = false) String search, HttpSession session) {
		if (search != null || !search.isEmpty()) {
			session.setAttribute("searchProdut", productService.findBySearch(search));
		}
		session.setAttribute("page", "search/search");
		return "index";
	}

	@RequestMapping("/cart-index")
	public String cart(HttpSession session) {
		session.setAttribute("page", "/cart/cart-index");
		return "index";
	}

	@RequestMapping("/category")
	public String danhmuc(HttpSession session) {
		session.setAttribute("page", "/category/category");
		return "index";
	}

	@RequestMapping("/account")
	public String upaccount(HttpSession session) {
		session.setAttribute("page", "/update_profile/account_profile");
		return "index";
	}

	@RequestMapping("/password")
	public String uppass(HttpSession session) {
		session.setAttribute("page", "/update_profile/password_profile");
		return "index";
	}

	@RequestMapping("/payment")
	public String giaodich(HttpSession session) {
		session.setAttribute("page", "/update_profile/payment_profile");
		return "index";
	}

	@RequestMapping("/order")
	public String donhang(HttpSession session) {
		session.setAttribute("page", "/update_profile/order_profile");
		return "index";
	}

	@RequestMapping("/comment")
	public String binhluan(HttpSession session) {
		session.setAttribute("page", "/update_profile/comment_profile");
		return "index";
	}

	@RequestMapping("/favorite")
	public String sanphamyeuthich(HttpSession session) {
		session.setAttribute("page", "/update_profile/favorite_profile");
		return "index";
	}

	@RequestMapping("/introduct")
	public String gioithieu(HttpSession session) {
		session.setAttribute("page", "/update_profile/introduct_profile");
		return "index";
	}

	@RequestMapping("/detailProduct")
	public String detailProduct(HttpSession session) {
		session.setAttribute("page", "/detailProduct/detailProduct");
		return "index";
	}

	@RequestMapping("/detail_profile")
	public String detail_profile(HttpSession session) {
		session.setAttribute("page", "/update_profile/detail_profile");
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
