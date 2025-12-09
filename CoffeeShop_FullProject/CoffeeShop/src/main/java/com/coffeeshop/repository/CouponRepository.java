package com.coffeeshop.repository;

import com.coffeeshop.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    Coupon findByCode(String code);
}
