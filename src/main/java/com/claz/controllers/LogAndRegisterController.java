package com.claz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogAndRegisterController {
    @GetMapping("/login")
    public String login() {
        return "LoginAndRegister";
    }
    @GetMapping("/register")
    public String register() {
        return "LoginAndRegister";
    }
}
