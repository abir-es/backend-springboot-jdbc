package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingPrice;

import java.util.List;

public interface ProductOfferingPriceDao {
    void saveProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices);
    void updateProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices);
    List<ProductOfferingPrice> getProductOfferingPricesByProductId(String productId);
    void deleteByProductId(String productId);
}
