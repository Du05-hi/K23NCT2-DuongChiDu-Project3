package com.coffeeshop.repository;

import com.coffeeshop.model.Order;
import com.coffeeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
