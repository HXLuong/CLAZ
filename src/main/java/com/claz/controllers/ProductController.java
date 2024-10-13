package com.claz.controllers;

import com.claz.models.Product;
import com.claz.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index"; // Trả về template Thymeleaf
    }

//    @GetMapping("/{id}")
//    public String showProduct(@PathVariable int id, Model model) {
//        Product product = productService.getProductById(id);
//        model.addAttribute("product", product);
//        return "detailProduct/detailProduct"; // Template để hiển thị chi tiết sản phẩm
//    }
//
//    @GetMapping("/create")
//    public String createProductForm(Model model) {
//        model.addAttribute("product", new Product());
//        return "product/create"; // Template để tạo sản phẩm mới
//    }
//
//    @PostMapping
//    public String saveProduct(@ModelAttribute Product product) {
//        productService.saveProduct(product);
//        return "redirect:/products"; // Chuyển hướng về danh sách sản phẩm
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable int id) {
//        productService.deleteProduct(id);
//        return "redirect:/products"; // Chuyển hướng về danh sách sản phẩm
//    }
}
