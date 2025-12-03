package com.coffeeshop.repository;

import com.coffeeshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Lấy tất cả sản phẩm theo category (không phân trang)
    List<Product> findByCategoryId(Integer categoryId);

    // ⭐ Lấy sản phẩm theo category + phân trang
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
