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
	@RequestMapping("/danhmuc")
    public String danhmuc(Model model) {
        return "danhmuc";
    }
}
