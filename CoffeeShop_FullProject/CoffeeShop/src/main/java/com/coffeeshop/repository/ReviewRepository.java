package com.coffeeshop.repository;

import com.coffeeshop.model.Product;
import com.coffeeshop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Lấy danh sách review theo sản phẩm, mới nhất trước
    List<Review> findByProductOrderByCreatedAtDesc(Product product);

    // ⭐ THÊM MỚI: Lấy toàn bộ review, sắp xếp theo thời gian mới nhất
    List<Review> findAllByOrderByCreatedAtDesc();
}
