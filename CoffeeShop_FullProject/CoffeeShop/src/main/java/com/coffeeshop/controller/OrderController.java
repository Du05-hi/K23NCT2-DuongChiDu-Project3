package com.coffeeshop.controller;

import com.coffeeshop.model.Order;
import com.coffeeshop.model.User;
import com.coffeeshop.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // ================= ORDERS LIST CŨ =====================
    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("orders", orderRepository.findByUser(user));
        return "user/orders";
    }

    // ================= LỊCH SỬ ĐẶT HÀNG (PHÂN TRANG) =====================
    @GetMapping("/orders/history")
    public String orderHistory(
            HttpSession session,
            Model model,
            @RequestParam(defaultValue = "0") int page
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        Page<Order> orderPage =
                orderRepository.findByUser_IdOrderByIdDesc(
                        user.getId(),
                        PageRequest.of(page, 5)
                );

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "user/order_history";
    }

    // ================= CHI TIẾT ĐƠN HÀNG =====================
    @GetMapping("/orders/detail")
    public String orderDetail(
            @RequestParam("id") Integer id,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/orders/history";
        }

        model.addAttribute("order", order);
        model.addAttribute("items", order.getOrderDetails());

        return "user/order_detail";
    }
}
