package com.practice.backend.dao;

import com.practice.backend.entity.ValidFor;

public interface ValidForDao {
    ValidFor saveValidFor(ValidFor validFor);
    ValidFor updateValidFor(ValidFor validFor);
    ValidFor findByProductOfferingPriceId(String productOfferingPriceId);
    void deleteByProductOfferingPriceId(String productOfferingPriceId);
}
