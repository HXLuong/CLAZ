package com.claz.controllers;

import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private ProductServiceImpl productService;
    
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;
    
    @GetMapping("/category")
    public String danhmuc(Model model, HttpSession session) {
    	List<Product> allpr = productService.findAll();
    	model.addAttribute("allproducts",allpr);
    	session.setAttribute("page", "/category/category");
        List<Category> cate = categoryServiceImpl.findAll();
        model.addAttribute("cates", cate);
        return "index";
    }
	
	@GetMapping("/category/{id}")
	public String catebyID(Model model,HttpSession session, @PathVariable int id) {
		List<Product> getByDM = productService.findAll().stream().filter(e -> e.getCategory().getId() == id).toList();
		model.addAttribute("allproducts",getByDM);
		List<Category> cate = categoryServiceImpl.findAll();
		model.addAttribute("cates", cate);
		session.setAttribute("page", "/category/category");
		return "index";
	}

   
}
