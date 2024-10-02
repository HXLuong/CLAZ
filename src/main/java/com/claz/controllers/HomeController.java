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
	@RequestMapping("/ac")
    public String upaccount(Model model) {
        return "/update_profile/capnhaptaikhoan";
    }
	@RequestMapping("/pass")
    public String uppass(Model model) {
        return "/update_profile/capnhapmatkhau";
    }
	@RequestMapping("/giaodich")
    public String giaodich(Model model) {
        return "/update_profile/lichsugiaodich";
    }
	@RequestMapping("/donhang")
    public String donhang(Model model) {
        return "/update_profile/lichsudonhang";
    }
	@RequestMapping("/binhluan")
    public String binhluan(Model model) {
        return "/update_profile/binhluan";
    }
	@RequestMapping("/sanphamyeuthich")
    public String sanphamyeuthich(Model model) {
        return "/update_profile/sanphamyeuthich";
    }
	@RequestMapping("/gioithieubanbe")
    public String gioithieu(Model model) {
        return "/update_profile/gioithieubanbe";
    }
	@RequestMapping("/huongdantaotaikhoan")
    public String huongdantaotaikhoan(Model model) {
        return "huongdantaotaikhoan";
    }
}
