package com.coffeeshop.controller;

import com.coffeeshop.model.Product;
import com.coffeeshop.model.Review;
import com.coffeeshop.model.User;
import com.coffeeshop.service.ProductService;
import com.coffeeshop.service.ReviewService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    // =====================
    // ⭐ THÊM REVIEW
    // =====================
    @PostMapping("/add")
    public String addReview(@RequestParam("productId") Integer productId,
                            @RequestParam("rating") Integer rating,
                            @RequestParam("comment") String comment,
                            HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) return "redirect:/login";

        Product product = productService.findById(productId);

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setUser(currentUser);
        review.setProduct(product);

        reviewService.save(review);

        return "redirect:/product/" + productId;
    }


    // =====================
    // ⭐ LỊCH SỬ ĐÁNH GIÁ CỦA USER
    // =====================
    @GetMapping("/history")
    public String myReviews(Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) return "redirect:/login";

        model.addAttribute("reviews",
                reviewService.findByUser(currentUser));

        return "user/reviews-list";
    }

    // =====================
    // ⭐ TẤT CẢ ĐÁNH GIÁ (FIX LỖI 404)
    // =====================
    @GetMapping("/all")
    public String allReviews(Model model) {

        model.addAttribute("reviews", reviewService.findAllReviews());


        return "user/reviews-list";
    }
}
