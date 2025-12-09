package com.coffeeshop.service;

import com.coffeeshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findByCategory(Integer categoryId);

    // ⭐ Lấy sản phẩm theo category + phân trang
    Page<Product> findByCategoryPaged(Integer categoryId, Pageable pageable);

    // ⭐ HÀM MỚI – FILTER + SEARCH + SORT
    Page<Product> filterByCategory(Integer categoryId, String keyword, String sort, Pageable pageable);

    Page<Product> filterAll(String keyword, String sort, Pageable pageable);

    void save(Product product);

    void delete(Integer id);
}
