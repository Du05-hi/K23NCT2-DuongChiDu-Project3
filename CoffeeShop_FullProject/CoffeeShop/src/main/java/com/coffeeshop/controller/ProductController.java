package com.coffeeshop.controller;

import com.coffeeshop.model.Product;
import com.coffeeshop.repository.CategoryRepository;
import com.coffeeshop.repository.ProductRepository;
import com.coffeeshop.repository.ToppingRepository;
import com.coffeeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    // ================= LIST ==================
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "user/product-list";
    }

    // =============== VIEW BY CATEGORY (KHÔNG TRÙNG NỮA) ===============
    @GetMapping("/products/category/{id}")
    public String listByCategory(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("products", productService.findByCategory(id));
        model.addAttribute("categories", categoryRepository.findAll());

        return "user/product-list";
    }

    // =============== DETAIL PAGE ===============
    @GetMapping("/product/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {

        Product product = productService.findById(id);
        if (product == null) return "redirect:/products";

        model.addAttribute("product", product);

        // ================= RELATED PRODUCTS =================
        if (product.getCategory() != null) {

            List<Product> related =
                    productRepository.findByCategoryId(product.getCategory().getId())
                            .stream()
                            .filter(p -> !p.getId().equals(product.getId()))
                            .limit(4)
                            .collect(Collectors.toList());

            model.addAttribute("related", related);
        }

        // ================ TOPPING LIST ================
        model.addAttribute("toppings", toppingRepository.findAll());

        return "user/product-detail";
    }
}
