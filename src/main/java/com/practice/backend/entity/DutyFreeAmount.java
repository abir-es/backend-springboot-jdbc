
package com.practice.backend.entity;

public class DutyFreeAmount {
    private int id;
    private String unit;
    private Double value;
    private int priceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    @Override
    public String toString() {
        return "DutyFreeAmount{" +
                "unit='" + unit + '\'' +
                ", value=" + value +
                ", priceId=" + priceId +
                '}';
    }

}
