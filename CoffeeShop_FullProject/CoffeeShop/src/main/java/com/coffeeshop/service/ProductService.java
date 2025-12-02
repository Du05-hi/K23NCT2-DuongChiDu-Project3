package com.coffeeshop.service;

import com.coffeeshop.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findByCategory(Integer categoryId);

    void save(Product product);

    void delete(Integer id);
}
