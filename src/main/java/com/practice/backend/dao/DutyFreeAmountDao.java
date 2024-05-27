package com.practice.backend.dao;

import com.practice.backend.entity.DutyFreeAmount;

public interface DutyFreeAmountDao {
    DutyFreeAmount saveDutyFreeAmount(DutyFreeAmount dutyFreeAmount);
    DutyFreeAmount updateDutyFreeAmount(DutyFreeAmount dutyFreeAmount);
    DutyFreeAmount findByPriceId(int priceId);
    void deleteByPriceId(int priceId);
}
