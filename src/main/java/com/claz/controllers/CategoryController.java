package com.claz.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.repository.CategoryRepository;
import com.claz.repository.ProductRepository;
import com.claz.services.CategoryService;
import com.claz.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
	
	private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
	
	@RequestMapping("/category")
    public String danhmuc(Model model) {
    	List<Product> allpr = productService.getAllProduct();
        model.addAttribute("allproducts",allpr);
        model.addAttribute("page", "/category/category");
        List<Category> cate = categoryRepository.findAll();
        model.addAttribute("cates", cate);
        return "index";
    }
	
	@GetMapping("/category/{id}")
	public String catebyID(Model model, @PathVariable int id) {
		List<Product> getByDM = productService.getAllProduct().stream().filter(e -> e.getCategory().getId() == id).toList();
		model.addAttribute("allproducts",getByDM);
		List<Category> cate = categoryRepository.findAll();
        model.addAttribute("cates", cate);
		model.addAttribute("page", "/category/category");
		return "index";
	}
}
