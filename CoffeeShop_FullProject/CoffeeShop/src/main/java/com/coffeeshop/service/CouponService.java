package com.coffeeshop.service;

import com.coffeeshop.model.Coupon;
import com.coffeeshop.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public Coupon checkValidCoupon(String code, Double orderTotal) {

        Coupon coupon = couponRepository.findByCode(code);

        if (coupon == null) return null;                     // Không tồn tại
        if (coupon.getStatus() == 0) return null;            // Bị khóa
        if (coupon.getExpiredAt().isBefore(LocalDateTime.now())) return null;  // Hết hạn
        if (orderTotal < coupon.getMinOrder()) return null;  // Không đủ điều kiện

        return coupon;
    }
}
