package com.practice.backend.dao;

import com.practice.backend.entity.ValidFor;

public interface ValidForDao {
    void saveValidFor(ValidFor validFor);
    void updateValidFor(ValidFor validFor);
    ValidFor findByProductOfferingPriceId(String productOfferingPriceId);
    void deleteByProductOfferingPriceId(String productOfferingPriceId);
}
