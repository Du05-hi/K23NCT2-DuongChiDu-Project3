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
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    // ================== THÊM REVIEW ==================
    @PostMapping("/add")
    public String addReview(@RequestParam("productId") Integer productId,
                            @RequestParam("rating") Integer rating,
                            @RequestParam("comment") String comment,
                            HttpSession session) {

        // Lấy user từ SESSION
        User currentUser = (User) session.getAttribute("currentUser");

        // Nếu chưa đăng nhập → chuyển đến login
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Lấy sản phẩm
        Product product = productService.findById(productId);

        // Tạo review mới
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setUser(currentUser);
        review.setProduct(product);

        // Lưu xuống DB
        reviewService.save(review);

        // Quay lại trang chi tiết
        return "redirect:/product/" + productId;
    }

    // ================== TRANG HIỂN THỊ TẤT CẢ ĐÁNH GIÁ ==================
    @GetMapping("/all")
    public String listAllReviews(Model model) {

        model.addAttribute("reviews", reviewService.findAllReviews());

        return "user/review-list"; // View hiển thị danh sách Review
    }
}
