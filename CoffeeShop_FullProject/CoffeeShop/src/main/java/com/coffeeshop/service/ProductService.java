package com.coffeeshop.service;

import com.coffeeshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findByCategory(Integer categoryId);

    // ⭐ HÀM MỚI: Lấy sản phẩm theo category + phân trang
    Page<Product> findByCategoryPaged(Integer categoryId, Pageable pageable);

    void save(Product product);

    void delete(Integer id);
}
