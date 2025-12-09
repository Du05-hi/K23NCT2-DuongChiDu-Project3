package com.coffeeshop.controller;

import com.coffeeshop.model.Coupon;
import com.coffeeshop.service.CouponService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/apply")
    public String applyCoupon(@RequestParam("code") String code,
                              HttpSession session) {

        Double total = (Double) session.getAttribute("cartTotal"); // tổng giỏ hàng

        Coupon coupon = couponService.checkValidCoupon(code, total);

        if (coupon == null) {
            session.setAttribute("couponError", "Mã giảm giá không hợp lệ!");
            return "redirect:/cart";
        }

        // Lưu vào session
        session.setAttribute("coupon", coupon);
        session.setAttribute("discount", coupon.getDiscountValue());

        return "redirect:/cart";
    }
}
