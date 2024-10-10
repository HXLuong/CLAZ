package com.claz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManagerController {
    @RequestMapping("/admin")
    public String adm(Model model) {
    	model.addAttribute("page", "/admin/dashboard");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminCategory")
    public String adminCategory(Model model) {
    	model.addAttribute("page", "/admin/admin-category");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminProduct")
    public String adminProduct(Model model) {
    	model.addAttribute("page", "/admin/admin-product");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminCustomer")
    public String adminCustomer(Model model) {
    	model.addAttribute("page", "/admin/admin-customer");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminListProduct")
    public String adminListProduct(Model model) {
    	model.addAttribute("page", "/admin/admin-listProduct");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminListCustomer")
    public String adminListCustomer(Model model) {
    	model.addAttribute("page", "/admin/admin-listCustomer");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminListCategory")
    public String adminListCategory(Model model) {
    	model.addAttribute("page", "/admin/admin-listCategory");
        return "/admin/admin-index";
    }
    
    @RequestMapping("/adminListOrder")
    public String adminListOrder(Model model) {
    	model.addAttribute("page", "/admin/admin-listOrder");
        return "/admin/admin-index";
    }
}
