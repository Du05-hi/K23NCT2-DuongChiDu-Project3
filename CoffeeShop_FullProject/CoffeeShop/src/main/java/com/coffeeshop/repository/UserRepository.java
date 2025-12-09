package com.coffeeshop.repository;

import com.coffeeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    // ============================
    // 🔎 Kiểm tra email tồn tại
    // ============================
    User findByEmail(String email);

    // ============================
    // 🔐 Đăng nhập
    // ============================
    User findByEmailAndPassword(String email, String password);

    // ============================
    // 👥 Đếm số người dùng
    // ============================
    @Query("SELECT COUNT(u) FROM User u")
    Long countUsers();
}
