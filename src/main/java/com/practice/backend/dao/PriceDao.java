package com.practice.backend.dao;

import com.practice.backend.entity.Price;

public interface PriceDao {
    Price savePrice(String offeringPriceId, Price price);
    Price updatePrice(Price price);
    Price findByProductOfferingPriceId(String productOfferingPriceId);
    void deleteById(int id);
}
