package com.claz.controllers;

import com.claz.serviceImpls.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam(name = "search", required = false) String search, HttpSession session) {
        if(search != null || !search.isEmpty()) {
            session.removeAttribute("products");
            session.setAttribute("searchProdut", productService.findBySearch(search));
            return "search/search";
        }else {
            session.removeAttribute("searchProdut");
            return "redirect:/";
        }
    }
}