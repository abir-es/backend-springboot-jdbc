package com.practice.backend.dao;

import com.practice.backend.entity.Price;

public interface PriceDao {
    Integer savePrice(String offeringPriceId, Price price);
    void updatePrice(Price price);
    Price findByProductOfferingPriceId(String productOfferingPriceId);
    void deleteById(int id);
}
