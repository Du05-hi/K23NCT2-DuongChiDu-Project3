package com.coffeeshop.service;

import com.coffeeshop.model.Product;
import com.coffeeshop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<Product> findByCategoryPaged(Integer categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }


    // ======================= ⭐ FILTER CATEGORY =======================
    @Override
    public Page<Product> filterByCategory(Integer categoryId, String keyword, String sort, Pageable pageable) {

        if (keyword != null && !keyword.isEmpty()) {
            if ("asc".equals(sort))
                return productRepository.filterCategoryKeywordPriceAsc(categoryId, keyword, pageable);

            if ("desc".equals(sort))
                return productRepository.filterCategoryKeywordPriceDesc(categoryId, keyword, pageable);

            return productRepository.filterCategoryKeyword(categoryId, keyword, pageable);
        }

        if ("asc".equals(sort))
            return productRepository.filterCategoryPriceAsc(categoryId, pageable);

        if ("desc".equals(sort))
            return productRepository.filterCategoryPriceDesc(categoryId, pageable);

        return productRepository.findByCategoryId(categoryId, pageable);
    }


    // ======================= ⭐ FILTER TOÀN BỘ =======================
    @Override
    public Page<Product> filterAll(String keyword, String sort, Pageable pageable) {

        if (keyword != null && !keyword.isEmpty()) {
            if ("asc".equals(sort))
                return productRepository.searchKeywordPriceAsc(keyword, pageable);

            if ("desc".equals(sort))
                return productRepository.searchKeywordPriceDesc(keyword, pageable);

            return productRepository.searchKeyword(keyword, pageable);
        }

        if ("asc".equals(sort))
            return productRepository.sortPriceAsc(pageable);

        if ("desc".equals(sort))
            return productRepository.sortPriceDesc(pageable);

        return productRepository.findAll(pageable);
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
