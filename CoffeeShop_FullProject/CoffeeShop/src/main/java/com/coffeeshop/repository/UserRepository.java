package com.coffeeshop.repository;

import com.coffeeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Tìm user theo email để kiểm tra đăng ký trùng email
    User findByEmail(String email);

    // ⭐ HÀM ĐĂNG NHẬP ĐÚNG CHUẨN – QUAN TRỌNG NHẤT ⭐
    User findByEmailAndPassword(String email, String password);
}
