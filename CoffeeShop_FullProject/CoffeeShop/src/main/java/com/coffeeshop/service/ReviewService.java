package com.coffeeshop.service;

import com.coffeeshop.model.Product;
import com.coffeeshop.model.Review;
import com.coffeeshop.model.User;
import com.coffeeshop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findByProductOrderByCreatedAtDesc(product);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    // ⭐⭐ LẤY REVIEW THEO USER (DÙNG CHO lịch sử đánh giá)
    public List<Review> findByUser(User user) {
        return reviewRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // ⭐⭐ TRANG TẤT CẢ REVIEW (nếu bạn cần trang tổng)
    public List<Review> findAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    // ⭐ Tính điểm trung bình số sao của sản phẩm
    public Double getAverageRating(Product product) {
        List<Review> reviews = getReviewsByProduct(product);
        if (reviews.isEmpty()) return 0.0;

        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        return sum / reviews.size();
    }

    // ⭐ Đếm số lượng đánh giá
    public int countByProduct(Product product) {
        return getReviewsByProduct(product).size();
    }
}
