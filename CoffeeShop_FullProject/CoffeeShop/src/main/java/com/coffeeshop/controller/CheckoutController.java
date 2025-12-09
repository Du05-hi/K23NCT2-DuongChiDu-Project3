package com.coffeeshop.controller;

import com.coffeeshop.dto.CartItem;
import com.coffeeshop.model.Coupon;
import com.coffeeshop.model.Order;
import com.coffeeshop.model.OrderDetail;
import com.coffeeshop.model.Topping;
import com.coffeeshop.model.User;
import com.coffeeshop.repository.OrderDetailRepository;
import com.coffeeshop.repository.OrderRepository;
import com.coffeeshop.service.CartService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {

    private final OrderRepository orderRepo;
    private final OrderDetailRepository detailRepo;
    private final CartService cartService;

    public CheckoutController(OrderRepository orderRepo,
                              OrderDetailRepository detailRepo,
                              CartService cartService) {
        this.orderRepo = orderRepo;
        this.detailRepo = detailRepo;
        this.cartService = cartService;
    }

    // ============================
    // ⭐ HIỂN THỊ TRANG CHECKOUT
    // ============================
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        if (cartService.getItems().isEmpty()) return "redirect:/cart";

        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        model.addAttribute("coupon", session.getAttribute("coupon"));
        model.addAttribute("discount", session.getAttribute("discount"));

        return "user/checkout";
    }

    // ============================
    // ⭐ XỬ LÝ ĐẶT HÀNG
    // ============================
    @PostMapping("/checkout/process")
    public String processCheckout(
            @RequestParam("customerName") String customerName,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("paymentMethod") String paymentMethod,
            HttpSession session,
            Model model
    ) {

        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        List<CartItem> cart = new ArrayList<>(cartService.getItems());
        if (cart.isEmpty()) return "redirect:/cart";

        double originalTotal = cartService.getTotal();

        // ==========================================
        // ⭐ LẤY COUPON TỪ SESSION
        // ==========================================
        Coupon coupon = (Coupon) session.getAttribute("coupon");
        Double discountAmount = 0.0;

        if (coupon != null) {
            discountAmount = originalTotal * coupon.getDiscountValue() / 100;
        }

        double finalTotal = originalTotal - discountAmount;

        // ==========================================
        // ⭐ TẠO ĐƠN HÀNG
        // ==========================================
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());

        // ⭐ LƯU TỔNG TIỀN SAU GIẢM
        order.setTotal(finalTotal);

        // ⭐ LƯU COUPON VÀO ORDER
        if (coupon != null) {
            order.setCouponCode(coupon.getCode());
            order.setDiscountAmount(discountAmount);
        }

        // ⭐ GẮN USER
        order.setUser(user);

        orderRepo.save(order);

        // ==========================================
        // ⭐ LƯU CHI TIẾT ĐƠN HÀNG
        // ==========================================
        List<OrderDetail> detailList = new ArrayList<>();

        for (CartItem item : cart) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setPrice(item.getProduct().getPrice());
            detail.setQuantity(item.getQuantity());

            detail.setSize(item.getSize());
            detail.setSugar(item.getSugar());
            detail.setIce(item.getIce());

            // ⭐ Toppings
            if (item.getToppings() != null && !item.getToppings().isEmpty()) {
                String names = item.getToppings()
                        .stream()
                        .map(Topping::getName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                detail.setToppingNames(names);
            }

            detailRepo.save(detail);
            detailList.add(detail);
        }

        order.setOrderDetails(detailList);
        orderRepo.save(order);

        // ==========================================
        // ⭐ XOÁ GIỎ HÀNG + XOÁ COUPON
        // ==========================================
        session.removeAttribute("coupon");
        session.removeAttribute("discount");

        cartService.clear();

        model.addAttribute("order", order);
        return "user/order-success";
    }
}
