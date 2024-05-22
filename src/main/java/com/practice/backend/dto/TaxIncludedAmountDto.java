
package com.practice.backend.dto;

public class TaxIncludedAmountDto {
    private String unit;
    private Double value;

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
}
