package com.coffeeshop.service;

import com.coffeeshop.model.Coupon;
import com.coffeeshop.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository repo;

    public CouponService(CouponRepository repo) {
        this.repo = repo;
    }

    // ================= ADMIN =================

    public List<Coupon> findAll() {
        return repo.findAll();
    }

    public Coupon findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Coupon save(Coupon coupon) {
        return repo.save(coupon);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // ================= USER / CHECKOUT =================

    /**
     * Kiểm tra mã coupon có hợp lệ không
     * @param code mã coupon
     * @param orderTotal tổng tiền đơn hàng
     * @return Coupon hợp lệ hoặc null
     */
    public Coupon checkValidCoupon(String code, Double orderTotal) {

        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        Optional<Coupon> opt = repo.findByCode(code.trim());

        if (opt.isEmpty()) {
            return null; // không tồn tại
        }

        Coupon coupon = opt.get();

        // kiểm tra trạng thái
        if (!"ACTIVE".equalsIgnoreCase(coupon.getStatus())) {
            return null;
        }

        // kiểm tra hết hạn
        if (coupon.getExpiredAt() != null &&
                coupon.getExpiredAt().isBefore(LocalDate.now())) {
            return null;
        }

        // kiểm tra đơn tối thiểu
        if (coupon.getMinOrder() > 0 &&
                orderTotal < coupon.getMinOrder()) {
            return null;
        }


        return coupon; // hợp lệ
    }
}
