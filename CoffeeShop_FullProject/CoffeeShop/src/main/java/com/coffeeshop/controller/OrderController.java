package com.coffeeshop.controller;

import com.coffeeshop.model.Order;
import com.coffeeshop.model.OrderDetail;
import com.coffeeshop.model.User;
import com.coffeeshop.repository.OrderDetailRepository;
import com.coffeeshop.repository.OrderRepository;
import com.coffeeshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderController(CartService cartService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        if (cartService.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "user/checkout";
    }

    @PostMapping("/checkout")
    public String doCheckout(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        if (cartService.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");
        order.setUser(user);
        order.setTotal(cartService.getTotal());
        Order saved = orderRepository.save(order);

        cartService.getItems().stream().map(item -> {
            OrderDetail d = new OrderDetail();
            d.setOrder(saved);
            d.setProduct(item.getProduct());
            d.setPrice(item.getProduct().getPrice());
            d.setQuantity(item.getQuantity());
            return d;
        }).collect(Collectors.toList()).forEach(orderDetailRepository::save);

        cartService.clear();

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("orders", orderRepository.findByUser(user));
        return "user/orders";
    }
}
