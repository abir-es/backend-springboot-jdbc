
package com.practice.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductOfferingPriceDto {
    private String id;
    private String name;
    private String description;
    private int recurringChargePeriodLength;
    private Boolean isBundle;
    private String lastUpdate;
    private String lifecycleStatus;
    private int percentage;
    private String priceType;
    private String version;
    @JsonAlias("@type")
    private String type;
    private PriceDto priceDto;
    private UnitOfMeasureDto unitOfMeasureDto;
    private ValidForDto validForDto;

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

    public PriceDto getPrice() {
        return priceDto;
    }

    public void setPrice(PriceDto priceDto) {
        this.priceDto = priceDto;
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

    public UnitOfMeasureDto getUnitOfMeasure() {
        return unitOfMeasureDto;
    }

    public void setUnitOfMeasure(UnitOfMeasureDto unitOfMeasureDto) {
        this.unitOfMeasureDto = unitOfMeasureDto;
    }

    public ValidForDto getValidFor() {
        return validForDto;
    }

    public void setValidFor(ValidForDto validForDto) {
        this.validForDto = validForDto;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
