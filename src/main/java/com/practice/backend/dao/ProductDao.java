package com.practice.backend.dao;

import com.practice.backend.entity.Product;

import java.util.List;

public interface ProductDao {
    Product findById(String id);
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void deleteById(String id);
}
