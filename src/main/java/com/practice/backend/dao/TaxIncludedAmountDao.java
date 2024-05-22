package com.practice.backend.dao;

import com.practice.backend.entity.TaxIncludedAmount;

public interface TaxIncludedAmountDao {
    void saveTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount);
    void updateTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount);
    TaxIncludedAmount findByPriceId(int priceId);
    void deleteByPriceId(int priceId);
}
