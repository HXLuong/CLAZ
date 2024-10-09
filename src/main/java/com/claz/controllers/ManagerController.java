package com.claz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
    @GetMapping("/adm")
    public String adm() {
        return "/admin/SPLIST";
    }
}
