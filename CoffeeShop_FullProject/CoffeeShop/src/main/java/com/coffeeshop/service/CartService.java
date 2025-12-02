package com.coffeeshop.service;

import com.coffeeshop.dto.CartItem;
import com.coffeeshop.model.Product;
import com.coffeeshop.model.Topping;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final Map<Integer, CartItem> cart = new LinkedHashMap<>();

    /**
     * ========== HÀM CŨ ==========
     * Giữ nguyên để không lỗi chỗ khác
     */
    public void add(Product product, int quantity) {
        CartItem item = cart.get(product.getId());
        if (item == null) {
            // Default option (M / 100% / Bình thường / không topping)
            item = new CartItem(product, quantity,
                    "M", "100%", "Bình thường",
                    Collections.emptyList());
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        cart.put(product.getId(), item);
    }

    /**
     * ========== HÀM MỚI ==========
     * Hỗ trợ Size + Đường + Đá + Topping
     */
    public void add(Product product, int quantity,
                    String size, String sugar, String ice,
                    List<Topping> toppings) {

        // Key theo productId (nếu muốn phân biệt option bạn có thể dùng key phức tạp hơn)
        Integer key = product.getId();

        CartItem item = cart.get(key);
        if (item == null) {
            item = new CartItem(product, quantity, size, sugar, ice, toppings);
        } else {
            // Nếu bạn muốn gộp option khác nhau => sửa code tại đây
            item.setQuantity(item.getQuantity() + quantity);
        }
        cart.put(key, item);
    }


    public void update(Integer productId, int quantity) {
        CartItem item = cart.get(productId);
        if (item != null) {
            item.setQuantity(quantity);
        }
    }

    public void remove(Integer productId) {
        cart.remove(productId);
    }

    public void clear() {
        cart.clear();
    }

    public Collection<CartItem> getItems() {
        return cart.values();
    }

    public double getTotal() {
        return cart.values().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
