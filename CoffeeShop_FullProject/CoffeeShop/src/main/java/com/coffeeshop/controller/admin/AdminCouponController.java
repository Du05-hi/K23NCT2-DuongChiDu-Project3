package com.coffeeshop.controller.admin;

import com.coffeeshop.model.Coupon;
import com.coffeeshop.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/vouchers") // GIỮ URL cũ để menu khỏi đổi
public class AdminCouponController {

    private final CouponService couponService;

    public AdminCouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("vouchers", couponService.findAll()); // giữ biến vouchers cho khỏi sửa list.html nhiều
        return "admin/vouchers/list";
    }

    // CREATE
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("voucher", new Coupon()); // giữ tên voucher cho form.html đang bind
        return "admin/vouchers/form";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("voucher", couponService.findById(id));
        return "admin/vouchers/form";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute("voucher") Coupon coupon) {
        couponService.save(coupon);
        return "redirect:/admin/vouchers";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        couponService.deleteById(id);
        return "redirect:/admin/vouchers";
    }
}
