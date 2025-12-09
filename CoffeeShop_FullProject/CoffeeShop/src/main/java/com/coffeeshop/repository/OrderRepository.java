package com.coffeeshop.repository;

import com.coffeeshop.model.Order;
import com.coffeeshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(String status);

    List<Order> findByUserAndStatus(User user, String status);

    Page<Order> findByUser_IdOrderByIdDesc(Integer userId, Pageable pageable);


    // ========================
    // THỐNG KÊ TỔNG QUAN
    // ========================

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status = 'COMPLETED'")
    Double getTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o")
    Long getTotalOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'COMPLETED'")
    Long getSuccessOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'PENDING'")
    Long getPendingOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'CANCELLED'")
    Long getCancelOrders();


    // ========================
    // BIỂU ĐỒ DOANH THU
    // ========================

    @Query("SELECT MONTH(o.orderDate) AS month, SUM(o.total) " +
            "FROM Order o WHERE o.status = 'COMPLETED' " +
            "GROUP BY MONTH(o.orderDate) " +
            "ORDER BY MONTH(o.orderDate)")
    List<Object[]> revenueByMonth();

}
