package com.coffeeshop.controller;

import com.coffeeshop.service.CategoryService;
import com.coffeeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // ================= HOME =================
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());

        return "user/home";   // <---- home.html
    }

    // ================= VIEW CATEGORY =================
    @GetMapping("/category/{id}")
    public String viewCategory(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findByCategory(id));

        return "user/product-list";
    }
}
