package com.coffeeshop.repository;

import com.coffeeshop.model.Order;
import com.coffeeshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Code cũ
    List<Order> findByUser(User user);

    List<Order> findByStatus(String status);

    List<Order> findByUserAndStatus(User user, String status);


    // ⭐⭐ HÀM ĐÚNG – LỊCH SỬ ĐƠN HÀNG (PHÂN TRANG) ⭐⭐
    Page<Order> findByUser_IdOrderByIdDesc(Integer userId, Pageable pageable);
}
