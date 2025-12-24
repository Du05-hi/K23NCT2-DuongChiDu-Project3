package com.coffeeshop.controller;

import com.coffeeshop.model.Coupon;
import com.coffeeshop.service.CartService;
import com.coffeeshop.service.CouponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CartService cartService;

    public CouponController(CouponService couponService, CartService cartService) {
        this.couponService = couponService;
        this.cartService = cartService;
    }

    // =================================================
    // 📄 TRANG SĂN COUPON (USER) – GET /coupon
    // =================================================
    @GetMapping
    public String couponPage() {
        // Chỉ hiển thị trang, KHÔNG xử lý logic
        return "user/coupon";
    }

    // =================================================
    // 🎫 ÁP DỤNG COUPON TRONG GIỎ HÀNG – POST /coupon/apply-cart
    // =================================================
    @PostMapping("/apply-cart")
    public String applyCouponInCart(@RequestParam("code") String code,
                                    HttpSession session,
                                    RedirectAttributes redirect) {

        // 1️⃣ Tính tổng tiền từ CartService (KHÔNG lấy từ request)
        double total = cartService.getTotal();

        if (total <= 0) {
            redirect.addFlashAttribute("couponError", "Giỏ hàng trống");
            return "redirect:/cart";
        }

        // 2️⃣ Kiểm tra coupon hợp lệ
        Coupon coupon = couponService.checkValidCoupon(code, total);

        if (coupon == null) {
            redirect.addFlashAttribute("couponError", "Mã giảm giá không hợp lệ");
            return "redirect:/cart";
        }

        // 3️⃣ Tính giảm giá
        double discount = total * coupon.getDiscountValue() / 100.0;
        double finalTotal = Math.max(0, total - discount);

        // 4️⃣ Lưu kết quả vào session
        session.setAttribute("cartDiscount", discount);
        session.setAttribute("cartFinalTotal", finalTotal);

        redirect.addFlashAttribute("couponSuccess", "Áp dụng mã giảm giá thành công");

        // 5️⃣ Quay lại trang giỏ hàng
        return "redirect:/cart";
    }

    // =================================================
    // 🚫 CHẶN TRUY CẬP GET /coupon/apply-cart
    // =================================================
    @GetMapping("/apply-cart")
    public String applyCartGet() {
        return "redirect:/cart";
    }
}
