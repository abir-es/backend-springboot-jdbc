
package com.practice.backend.entity;

public class ValidFor {
    private int id;
    private String productOfferingPriceId;
    private String startDateTime;

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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Override
    public String toString() {
        return "ValidFor{" +
                "startDateTime='" + startDateTime + '\'' +
                '}';
    }
}
