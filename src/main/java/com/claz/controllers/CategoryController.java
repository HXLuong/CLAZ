package com.claz.controllers;

import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.services.CategoryService;
import com.claz.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CategoryController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category")
	public String danhmuc(Model model, HttpSession session) {
		List<Product> allpr = productService.findAll();
		model.addAttribute("allproducts", allpr);
		session.setAttribute("page", "/category/category");
		List<Category> cate = categoryService.findAll();
		model.addAttribute("cates", cate);
		return "index";
	}

	@GetMapping("/category/{id}")
	public String catebyID(Model model, HttpSession session, @PathVariable int id) {
		List<Product> getByDM = productService.findAll().stream().filter(e -> e.getCategory().getId() == id).toList();
		model.addAttribute("allproducts", getByDM);
		List<Category> cate = categoryService.findAll();
		model.addAttribute("cates", cate);
		session.setAttribute("page", "/category/category");
		return "index";
	}

}
