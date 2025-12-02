package com.coffeeshop.controller.admin;

import com.coffeeshop.model.Product;
import com.coffeeshop.service.ProductService;
import com.coffeeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ========== LIST ==========
    @GetMapping("/admin/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/product/list";
    }

    // ========== ADD FORM ==========
    @GetMapping("/admin/products/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product/form";
    }

    // ========== EDIT FORM ==========
    @GetMapping("/admin/products/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {

        Product product = productService.findById(id);
        model.addAttribute("product", product);

        model.addAttribute("categories", categoryService.findAll());

        return "admin/product/form";
    }

    // ========== SAVE (ADD + EDIT) ==========
    @PostMapping("/admin/products/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    // ========== DELETE ==========
    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
