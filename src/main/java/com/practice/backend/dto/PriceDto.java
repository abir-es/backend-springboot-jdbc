
package com.practice.backend.dto;

public class PriceDto {
    private int percentage;
    private String taxCategory;
    private int taxRate;
    private String unit;
    private Double value;
    private DutyFreeAmountDto dutyFreeAmountDto;
    private TaxIncludedAmountDto taxIncludedAmountDto;

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

    public DutyFreeAmountDto getDutyFreeAmount() {
        return dutyFreeAmountDto;
    }

    public void setDutyFreeAmount(DutyFreeAmountDto dutyFreeAmountDto) {
        this.dutyFreeAmountDto = dutyFreeAmountDto;
    }

    public TaxIncludedAmountDto getTaxIncludedAmount() {
        return taxIncludedAmountDto;
    }

    public void setTaxIncludedAmount(TaxIncludedAmountDto taxIncludedAmountDto) {
        this.taxIncludedAmountDto = taxIncludedAmountDto;
    }
}
