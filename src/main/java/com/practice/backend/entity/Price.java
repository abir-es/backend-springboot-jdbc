
package com.practice.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Price {
    private int id;
    @JsonIgnore
    private String productOfferingPriceId;
    private int percentage;
    private String taxCategory;
    private int taxRate;
    private String unit;
    private Double value;
    private DutyFreeAmount dutyFreeAmount;
    private TaxIncludedAmount taxIncludedAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductOfferingPriceId() {
        return productOfferingPriceId;
    }

    public void setProductOfferingPriceId(String productOfferingPriceId) {
        this.productOfferingPriceId = productOfferingPriceId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DutyFreeAmount getDutyFreeAmount() {
        return dutyFreeAmount;
    }

    public void setDutyFreeAmount(DutyFreeAmount dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
    }

    public TaxIncludedAmount getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }
}
