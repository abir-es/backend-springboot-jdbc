package com.practice.backend.dao;

import com.practice.backend.entity.TaxIncludedAmount;

public interface TaxIncludedAmountDao {
    TaxIncludedAmount saveTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount);
    TaxIncludedAmount updateTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount);
    TaxIncludedAmount findByPriceId(int priceId);
    void deleteByPriceId(int priceId);
}
