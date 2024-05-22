package com.practice.backend.service;

import com.practice.backend.dto.ProductDto;
import com.practice.backend.entity.Product;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(String id);
    List<ProductDto> getAllProducts();
    ProductDto saveProduct(Product product);
    ProductDto updateProduct(Product product, String id);
    void deleteProductById(String id);
}
