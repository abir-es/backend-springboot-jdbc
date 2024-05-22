package com.practice.backend.dao;

import com.practice.backend.entity.DutyFreeAmount;

public interface DutyFreeAmountDao {
    void saveDutyFreeAmount(DutyFreeAmount dutyFreeAmount);
    void updateDutyFreeAmount(DutyFreeAmount dutyFreeAmount);
    DutyFreeAmount findByPriceId(int priceId);
    void deleteByPriceId(int priceId);
}
