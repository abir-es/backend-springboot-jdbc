package com.practice.backend.dao;

import com.practice.backend.entity.UnitOfMeasure;

public interface UnitOfMeasureDao {
    UnitOfMeasure saveUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    UnitOfMeasure findByProductOfferingPriceId(String productOfferingPriceId);
    void deleteByProductOfferingPriceId(String productOfferingPriceId);
}
