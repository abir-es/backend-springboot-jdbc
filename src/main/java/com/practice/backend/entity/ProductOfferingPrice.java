
package com.practice.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

public class ProductOfferingPrice {
    private String id;
    @JsonIgnore
    private String productId;
    private String name;
    private String description;
    private int recurringChargePeriodLength;
    private Boolean isBundle;
    private String lastUpdate;
    private String lifecycleStatus;
    private int percentage;
    private String priceType;
    private String version;
    @SerializedName("@type")
    private String type;
    private Price price;
    private UnitOfMeasure unitOfMeasure;
    private ValidFor validFor;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getRecurringChargePeriodLength() {
        return recurringChargePeriodLength;
    }

    public void setRecurringChargePeriodLength(int recurringChargePeriodLength) {
        this.recurringChargePeriodLength = recurringChargePeriodLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public ValidFor getValidFor() {
        return validFor;
    }

    public void setValidFor(ValidFor validFor) {
        this.validFor = validFor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ProductOfferingPrice{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", recurringChargePeriodLength=" + recurringChargePeriodLength +
                ", isBundle=" + isBundle +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", lifecycleStatus='" + lifecycleStatus + '\'' +
                ", percentage=" + percentage +
                ", priceType='" + priceType + '\'' +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", unitOfMeasure=" + unitOfMeasure +
                ", validFor=" + validFor +
                '}';
    }
}
