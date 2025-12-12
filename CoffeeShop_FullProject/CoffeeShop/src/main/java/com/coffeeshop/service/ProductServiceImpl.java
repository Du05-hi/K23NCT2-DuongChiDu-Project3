package com.coffeeshop.service;

import com.coffeeshop.model.Product;
import com.coffeeshop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }


    @Override
    public List<Product> findByCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    /* ==========================================================
       ⭐ FILTER CATEGORY (KHÔNG PHÂN TRANG)
    ========================================================== */
    @Override
    public List<Product> filterByCategory(Integer categoryId, String keyword, String sort) {

        List<Product> list = productRepository.findByCategoryId(categoryId);

        if (keyword != null && !keyword.isEmpty()) {
            list = list.stream()
                    .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Sort theo giá
        if ("asc".equals(sort)) {
            list = list.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        }
        if ("desc".equals(sort)) {
            list = list.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        }

        return list;
    }


    /* ==========================================================
       ⭐ FILTER ALL PRODUCTS (KHÔNG PHÂN TRANG)
    ========================================================== */
    @Override
    public List<Product> filterAll(String keyword, String sort) {

        List<Product> list = productRepository.findAll();

        if (keyword != null && !keyword.isEmpty()) {
            list = list.stream()
                    .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if ("asc".equals(sort)) {
            list = list.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        }
        if ("desc".equals(sort)) {
            list = list.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        }

        return list;
    }


    @Override
    public void save(Product product) {
        productRepository.save(product);
    }


    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

}
