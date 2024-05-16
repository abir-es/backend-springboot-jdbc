package com.practice.backend.service;

import com.practice.backend.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(String id);
    List<Product> getAllProducts();
    void saveProduct(Product product);
    void updateProduct(Product product, String id);
    void deleteProductById(String id);
}
