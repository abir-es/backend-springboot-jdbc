
package com.practice.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UnitOfMeasure {
    private int id;
    @JsonIgnore
    private String productOfferingPriceId;
    private Double amount;
    private String units;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "UnitOfMeasure{" +
                "amount=" + amount +
                ", units='" + units + '\'' +
                '}';
    }
}
