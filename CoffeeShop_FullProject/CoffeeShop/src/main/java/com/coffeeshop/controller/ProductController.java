package com.coffeeshop.controller;

import com.coffeeshop.model.Product;
import com.coffeeshop.repository.CategoryRepository;
import com.coffeeshop.repository.ProductRepository;
import com.coffeeshop.repository.ToppingRepository;
import com.coffeeshop.service.ProductService;
import com.coffeeshop.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ReviewService reviewService;


    // ============================================================
    // ⭐ HIỂN THỊ TẤT CẢ SẢN PHẨM — KHÔNG PHÂN TRANG
    // ============================================================
    @GetMapping("/products")
    public String listProducts(Model model) {

        List<Product> products = productService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("reviewService", reviewService);

        return "user/product-list";
    }


    // ============================================================
    // ⭐ XEM SẢN PHẨM THEO LOẠI — KHÔNG PHÂN TRANG
    // ============================================================
    @GetMapping("/products/category/{id}")
    public String listByCategory(@PathVariable("id") Integer id,
                                 Model model) {

        List<Product> products = productService.findByCategory(id);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("categoryId", id);
        model.addAttribute("reviewService", reviewService);

        return "user/product-list";
    }


    // ============================================================
    // ⭐ LỌC + TÌM KIẾM + SẮP XẾP — KHÔNG PHÂN TRANG
    // ============================================================
    @GetMapping("/products/filter")
    public String filterProducts(Model model,
                                 @RequestParam(required = false) Integer categoryId,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String sort) {

        List<Product> products;

        if (categoryId != null) {
            products = productService.filterByCategory(categoryId, keyword, sort);
            model.addAttribute("categoryId", categoryId);
        } else {
            products = productService.filterAll(keyword, sort);
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("reviewService", reviewService);

        return "user/product-list";
    }


    // ============================================================
    // ⭐ CHI TIẾT SẢN PHẨM
    // ============================================================
    @GetMapping("/product/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {

        Product product = productService.findById(id);
        if (product == null) return "redirect:/products";

        model.addAttribute("product", product);

        // ===== SẢN PHẨM LIÊN QUAN =====
        if (product.getCategory() != null) {

            List<Product> related =
                    productRepository.findByCategoryId(product.getCategory().getId())
                            .stream()
                            .filter(p -> !p.getId().equals(product.getId()))
                            .limit(4)
                            .collect(Collectors.toList());

            model.addAttribute("related", related);
        }

        // ===== TOPPINGS =====
        model.addAttribute("toppings", toppingRepository.findAll());

        // ===== REVIEW =====
        model.addAttribute("reviews", reviewService.getReviewsByProduct(product));
        model.addAttribute("avgRating", reviewService.getAverageRating(product));
        model.addAttribute("reviewCount", reviewService.countByProduct(product));

        return "user/product-detail";
    }
}
