package com.coffeeshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime orderDate = LocalDateTime.now();

    private Double total;

    private String status = "PENDING"; // trạng thái mặc định

    // ⭐ THÔNG TIN KHÁCH HÀNG CHECKOUT ⭐
    private String customerName;
    private String phone;
    private String address;
    private String paymentMethod; // COD / BANK / MOMO / VISA

    // ⭐⭐ THÔNG TIN COUPON (MỚI THÊM) ⭐⭐
    private String couponCode;        // mã giảm giá
    private Double discountAmount;    // số tiền đã giảm

    // Giữ nguyên liên kết user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Giữ nguyên orderDetails
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;


    public Order() {}


    // =====================
    // GETTER + SETTER
    // =====================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // ⭐ THÔNG TIN KHÁCH HÀNG ⭐
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    // ⭐⭐ COUPON (GET/SET) ⭐⭐
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }


    // ===== USER =====
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // ===== ORDER DETAILS =====
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
