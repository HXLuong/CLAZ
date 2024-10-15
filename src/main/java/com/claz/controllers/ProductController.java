package com.claz.controllers;

import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    
    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(HttpSession session,Model model) {
        List<Product> pr = productService.findAll().stream().filter(e -> e.getCategory().getId() == 1).toList();
        session.setAttribute("products", pr);
        List<Product> pr2 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 4).toList();
        session.setAttribute("gamesteam", pr2);
        List<Product> pr3 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 2).toList();
        session.setAttribute("lamviec", pr3);
        List<Product> pr4 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 3).toList();
        session.setAttribute("hoctap", pr4);
        List<Category> cate = categoryRepository.findAll();
        model.addAttribute("cates", cate);
        session.setAttribute("page", "component/home");
        return "index";
    }

    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam(name = "search", required = false) String search, HttpSession session) {
    if(search != null || !search.isEmpty()) {
        session.setAttribute("searchProdut", productService.findBySearch(search));
    }
        return "search/search";
    }
}
