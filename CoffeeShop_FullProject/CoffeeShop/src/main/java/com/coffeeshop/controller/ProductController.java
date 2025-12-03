package com.coffeeshop.controller;

import com.coffeeshop.model.Product;
import com.coffeeshop.repository.CategoryRepository;
import com.coffeeshop.repository.ProductRepository;
import com.coffeeshop.repository.ToppingRepository;
import com.coffeeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    // ===================== PRODUCT LIST + PAGINATION =====================
    @GetMapping("/products")
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page) {

        int pageSize = 8; // 8 sản phẩm / trang
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> productPage = productRepository.findAll(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        model.addAttribute("categories", categoryRepository.findAll());

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

        return "user/product-detail";
    }
}
