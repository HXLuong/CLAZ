package com.claz.controllers;

import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.services.CategoryService;
import com.claz.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category/{id}")
	public String catebyID(Model model, HttpSession session, @PathVariable("id") int id) {
		session.setAttribute("iddm", id);
		return "redirect:/category/pagidm";
	}

	@RequestMapping("/category/pagidm")
	public String page(Model model, HttpSession session, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		int id = (int) session.getAttribute("iddm");
		Page<Product> getByDM = productService.findbyDMandSort(id, pageable);
		session.setAttribute("allproducts", getByDM);
		List<Category> cate = categoryService.findAll();
		session.setAttribute("cates", cate);
		session.setAttribute("page", "/category/CategoryChoose");
		return "index";
	}

	@PostMapping("/product/searchprice")
	public String searchByPrice(Model model, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max, HttpSession session, @RequestParam("p") Optional<Integer> p) {
		double minPrice = min.orElse(0.0);
		System.out.println("aaa");
		double maxPrice = max.orElse(Double.MAX_VALUE);
		List<Product> products = productService.findByPrice(minPrice, maxPrice);
		session.setAttribute("allproducts", products);
		session.setAttribute("minprice", minPrice);
		session.setAttribute("maxprice", maxPrice);
		List<Category> cate = categoryService.findAll();
		session.setAttribute("cates", cate);
		session.setAttribute("page", "/category/CategoryPrice");
		return "index";
	}

	@RequestMapping("/sortsp")
	public String sort(Model model, @RequestParam(required = false) String sort, HttpSession session) {
		List<Product> products = productService.findAll();
		if ("asc".equals(sort)) {
			products.sort(Comparator.comparing(Product::getPrice)); // Sắp xếp giá từ thấp đến cao
		} else if ("desc".equals(sort)) {
			products.sort(Comparator.comparing(Product::getPrice).reversed()); // Sắp xếp giá từ cao đến thấp
		}
		session.setAttribute("allproducts", products);
		session.setAttribute("page", "/category/CategorySort");
		return "index";
	}

}
