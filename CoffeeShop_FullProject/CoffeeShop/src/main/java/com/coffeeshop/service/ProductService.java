package com.coffeeshop.service;

import com.coffeeshop.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findByCategory(Integer categoryId);

    // ⭐ Lọc theo category + keyword + sort (KHÔNG phân trang)
    List<Product> filterByCategory(Integer categoryId, String keyword, String sort);

    // ⭐ Lọc toàn bộ sản phẩm theo keyword + sort
    List<Product> filterAll(String keyword, String sort);

    void save(Product product);

    void delete(Integer id);
}
