package com.coffeeshop.controller;

import com.coffeeshop.model.Order;
import com.coffeeshop.model.User;
import com.coffeeshop.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ================= MY ORDERS =======================
    @GetMapping("/my-orders")
    public String myOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("orders", orderRepository.findByUser(user));
        return "user/my-orders";          // 💚 đúng tên file của bạn
    }


    // ================= DETAIL ==========================
    @GetMapping("/my-order-detail")
    public String myOrderDetail(
            @RequestParam("id") Integer id,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/my-orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("items", order.getOrderDetails());

        return "user/my-order-detail"; // 💚 đúng file
    }
}
