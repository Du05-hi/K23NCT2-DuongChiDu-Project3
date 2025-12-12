package com.coffeeshop.repository;

import com.coffeeshop.model.Product;
import com.coffeeshop.model.Review;
import com.coffeeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Lấy danh sách review theo sản phẩm, mới nhất trước
    List<Review> findByProductOrderByCreatedAtDesc(Product product);

    // ⭐⭐ Lấy danh sách theo User
    List<Review> findByUserOrderByCreatedAtDesc(User user);

    // ⭐⭐ Lấy toàn bộ review mới nhất
    List<Review> findAllByOrderByCreatedAtDesc();
}
