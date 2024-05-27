package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingRelationship;

import java.util.List;

public interface ProductOfferingRelationshipDao {
    List<ProductOfferingRelationship> saveProductOfferingRelationships(String productId, List<ProductOfferingRelationship> relationships);
    List<ProductOfferingRelationship> updateProductOfferingRelationships(String productId, List<ProductOfferingRelationship> relationships);
    List<ProductOfferingRelationship> getProductOfferingRelationshipsByProductId(String productId);
    void deleteByProductId(String productId);
}
