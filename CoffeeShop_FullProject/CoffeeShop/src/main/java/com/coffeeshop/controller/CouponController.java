package com.coffeeshop.controller;

import com.coffeeshop.model.Coupon;
import com.coffeeshop.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * Áp mã giảm giá khi checkout
     * URL: /coupon/apply
     */
    @PostMapping("/apply")
    public String applyCoupon(@RequestParam("code") String code,
                              @RequestParam("total") Double total,
                              Model model) {

        Coupon coupon = couponService.checkValidCoupon(code, total);

        if (coupon == null) {
            model.addAttribute("couponError", "Mã giảm giá không hợp lệ");
            model.addAttribute("total", total);
            return "user/checkout";
        }

        double discount = total * coupon.getDiscountValue() / 100;
        double finalTotal = total - discount;

        model.addAttribute("coupon", coupon);
        model.addAttribute("discount", discount);
        model.addAttribute("finalTotal", finalTotal);

        return "user/checkout";
    }
}
