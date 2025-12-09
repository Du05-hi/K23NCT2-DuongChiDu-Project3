package com.coffeeshop.controller;

import com.coffeeshop.model.Product;
import com.coffeeshop.model.Topping;
import com.coffeeshop.service.CartService;
import com.coffeeshop.service.ProductService;
import com.coffeeshop.repository.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ToppingRepository toppingRepository;

    @GetMapping("")
    public String viewCart(Model model, HttpSession session) {

        double total = cartService.getTotal();

        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", total);
        model.addAttribute("isEmpty", cartService.isEmpty());

        // ⭐ THÊM DÒNG QUAN TRỌNG: LƯU TOTAL VÀO SESSION
        session.setAttribute("cartTotal", total);

        return "user/cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam("productId") Integer productId,
            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "sugar", required = false) String sugar,
            @RequestParam(value = "ice", required = false) String ice,
            @RequestParam(value = "toppingIds", required = false) List<Integer> toppingIds) {

        Product product = productService.findById(productId);

        if (product != null) {

            // Nếu form cũ không gửi option → dùng mặc định
            if (size == null) size = "M";
            if (sugar == null) sugar = "100%";
            if (ice == null) ice = "Bình thường";

            List<Topping> toppings = new ArrayList<>();
            if (toppingIds != null && !toppingIds.isEmpty()) {
                toppings = toppingRepository.findAllById(toppingIds);
            }

            cartService.add(product, quantity, size, sugar, ice, toppings);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Integer productId,
                             @RequestParam("quantity") Integer quantity) {
        cartService.update(productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("productId") Integer productId) {
        cartService.remove(productId);
        return "redirect:/cart";
    }
}
