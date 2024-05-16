package com.practice.backend.dao;

import com.practice.backend.entity.UnitOfMeasure;

public interface UnitOfMeasureDao {
    void saveUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    void updateUnitOfMeasure(UnitOfMeasure unitOfMeasure);
    UnitOfMeasure findByProductOfferingPriceId(String productOfferingPriceId);
}
