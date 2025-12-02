package com.coffeeshop.repository;

import com.coffeeshop.model.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToppingRepository extends JpaRepository<Topping, Integer> {
}
