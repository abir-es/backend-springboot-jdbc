
package com.practice.backend.entity;

public class TaxIncludedAmount {
    private int id;
    private int priceId;
    private String unit;
    private Double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
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

    @Override
    public String toString() {
        return "TaxIncludedAmount{" +
                "unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }
}
