package com.coffeeshop.controller;

import com.coffeeshop.model.Product;
import com.coffeeshop.repository.CategoryRepository;
import com.coffeeshop.repository.ProductRepository;
import com.coffeeshop.repository.ToppingRepository;
import com.coffeeshop.service.ProductService;
import com.coffeeshop.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    // ===================== PRODUCT LIST + PAGINATION =====================
    @GetMapping("/products")
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page) {

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> productPage = productRepository.findAll(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categories", categoryRepository.findAll());

        // ⭐ THÊM: TRUYỀN reviewService để view lấy rating
        model.addAttribute("reviewService", reviewService);

        return "user/product-list";
    }


    // ===================== FILTER BY CATEGORY =====================
    @GetMapping("/products/category/{id}")
    public String listByCategory(@PathVariable("id") Integer id,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> productPage = productService.findByCategoryPaged(id, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("categoryId", id);

        // ⭐ THÊM: CHO VIEW TÍNH RATING
        model.addAttribute("reviewService", reviewService);

        return "user/product-list";
    }


    // ===================== ⭐ FILTER + SEARCH + SORT =====================
    @GetMapping("/products/filter")
    public String filterProducts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(required = false) Integer categoryId,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String sort) {

        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> productPage;

        // 1️⃣ Lọc theo Category
        if (categoryId != null) {
            productPage = productService.filterByCategory(categoryId, keyword, sort, pageable);
            model.addAttribute("categoryId", categoryId);
        }
        // 2️⃣ Tìm kiếm & sắp xếp toàn bộ
        else {
            productPage = productService.filterAll(keyword, sort, pageable);
        }

        // Truyền dữ liệu về giao diện
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);

        // ⭐ THÊM: GIÚP VIEW DÙNG reviewService
        model.addAttribute("reviewService", reviewService);

        return "user/product-list";
    }


    // ===================== PRODUCT DETAIL =====================
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

        // ================= TOPPINGS =================
        model.addAttribute("toppings", toppingRepository.findAll());

        // ================= REVIEWS =================
        model.addAttribute("reviews", reviewService.getReviewsByProduct(product));
        model.addAttribute("avgRating", reviewService.getAverageRating(product));
        model.addAttribute("reviewCount", reviewService.countByProduct(product));

        return "user/product-detail";
    }
}
