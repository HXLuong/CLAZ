package com.claz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/Dashboard")
public class ManagerController {
    @GetMapping("/adm")
    public String adm() {
        return "SPLIST";
    }
}
