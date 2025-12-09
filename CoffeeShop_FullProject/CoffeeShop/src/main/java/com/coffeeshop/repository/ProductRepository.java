package com.coffeeshop.repository;

import com.coffeeshop.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // ====================== CATEGORY ======================
    List<Product> findByCategoryId(Integer categoryId);

    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);


    // ====================== FILTER CATEGORY + KEYWORD ======================
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> filterCategoryKeyword(Integer categoryId, String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.price ASC")
    Page<Product> filterCategoryKeywordPriceAsc(Integer categoryId, String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.price DESC")
    Page<Product> filterCategoryKeywordPriceDesc(Integer categoryId, String keyword, Pageable pageable);


    // ====================== FILTER CATEGORY ONLY PRICE ======================
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p.price ASC")
    Page<Product> filterCategoryPriceAsc(Integer categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p.price DESC")
    Page<Product> filterCategoryPriceDesc(Integer categoryId, Pageable pageable);



    // ====================== SEARCH KEYWORD ======================
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchKeyword(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.price ASC")
    Page<Product> searchKeywordPriceAsc(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.price DESC")
    Page<Product> searchKeywordPriceDesc(String keyword, Pageable pageable);


    // ====================== SORT ALL ======================
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    Page<Product> sortPriceAsc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    Page<Product> sortPriceDesc(Pageable pageable);
}
