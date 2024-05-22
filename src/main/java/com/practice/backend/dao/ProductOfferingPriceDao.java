package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingPrice;

import java.util.List;

public interface ProductOfferingPriceDao {
    List<ProductOfferingPrice> saveProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices);
    List<ProductOfferingPrice> updateProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices);
    List<ProductOfferingPrice> getProductOfferingPricesByProductId(String productId);
    void deleteByProductId(String productId);
}
