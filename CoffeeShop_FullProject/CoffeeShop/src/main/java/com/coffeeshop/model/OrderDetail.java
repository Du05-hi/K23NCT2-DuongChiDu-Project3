package com.coffeeshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double price;
    private Integer quantity;

    // ⭐ THÊM 4 TRƯỜNG LƯU TÙY CHỌN SẢN PHẨM
    private String size;        // M / L
    private String sugar;       // 0% / 50% / 100%
    private String ice;         // Không đá / Ít đá / Nhiều đá
    private String toppingNames; // Ví dụ: "Trân châu, Kem cheese"


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderDetail() {}

    // ==========================
    // GET - SET
    // ==========================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // ⭐ getter + setter bổ sung
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

    public String getToppingNames() {
        return toppingNames;
    }

    public void setToppingNames(String toppingNames) {
        this.toppingNames = toppingNames;
    }
}
