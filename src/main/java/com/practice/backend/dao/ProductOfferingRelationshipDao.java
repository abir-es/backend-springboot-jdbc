package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingRelationship;

import java.util.List;

public interface ProductOfferingRelationshipDao {
    void saveProductOfferingRelationships(String productId, List<ProductOfferingRelationship> relationships);
    void updateProductOfferingRelationships(String productId, List<ProductOfferingRelationship> relationships);
    List<ProductOfferingRelationship> getProductOfferingRelationshipsByProductId(String productId);
}
