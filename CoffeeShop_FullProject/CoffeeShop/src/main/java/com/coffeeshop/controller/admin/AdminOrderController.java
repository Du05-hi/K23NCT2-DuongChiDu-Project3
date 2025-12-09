package com.coffeeshop.controller.admin;

import com.coffeeshop.model.Order;
import com.coffeeshop.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderRepository orderRepository;

    public AdminOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/order-list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return "redirect:/admin/orders";

        model.addAttribute("order", order);
        model.addAttribute("items", order.getOrderDetails());

        return "admin/order-detail";
    }

    // ⭐ CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG ⭐
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Integer id,
                               @RequestParam String status) {

        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }

        return "redirect:/admin/orders/detail/" + id;
    }
}
