package com.coffeeshop.dto;

import com.coffeeshop.model.Product;
import com.coffeeshop.model.Topping;

import java.util.ArrayList;
import java.util.List;

public class CartItem {

    private Product product;
    private int quantity;

    // OPTION mới
    private String size;   // M, L
    private String sugar;  // 0%, 50%, 100%
    private String ice;    // Không đá / Ít đá / Bình thường / Nhiều đá

    // Danh sách topping đã chọn
    private List<Topping> toppings = new ArrayList<>();

    public CartItem() {
    }

    public CartItem(Product product, int quantity,
                    String size, String sugar, String ice,
                    List<Topping> toppings) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.sugar = sugar;
        this.ice = ice;
        if (toppings != null) {
            this.toppings = toppings;
        }
    }

    // getter - setter
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    // Tổng tiền topping 1 ly
    public int getToppingTotal() {
        if (toppings == null) return 0;
        return toppings.stream()
                .mapToInt(t -> t.getPrice() != null ? t.getPrice() : 0)
                .sum();
    }

    // Phụ thu size
    public int getSizeExtra() {
        if ("L".equalsIgnoreCase(size)) {
            return 6000; // Size L +6k
        }
        return 0;
    }

    // Thành tiền 1 dòng
    public double getSubTotal() {
        if (product == null || product.getPrice() == null) {
            return 0.0;
        }
        double base = product.getPrice();
        double totalOne =
                base + getSizeExtra() + getToppingTotal();
        return totalOne * quantity;
    }
}
