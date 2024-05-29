
package com.practice.backend.entity;

import java.util.List;

public class Product {
    private String id;
    private String name;
    private String description;
    private Boolean isBundle;
    private Boolean isSellable;
    private String lastUpdate;
    private String lifecycleStatus;
    private int totalCount;
    private int version;
    private List<Channel> channel;
    private List<ProductOfferingPrice> productOfferingPrice;
    private List<ProductOfferingRelationship> productOfferingRelationship;

    public Product() {
    }

    public Product(String id, String name, String description, Boolean isBundle, Boolean isSellable, String lastUpdate, String lifecycleStatus, int totalCount, int version, List<Channel> channel, List<ProductOfferingPrice> productOfferingPrice, List<ProductOfferingRelationship> productOfferingRelationship) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isBundle = isBundle;
        this.isSellable = isSellable;
        this.lastUpdate = lastUpdate;
        this.lifecycleStatus = lifecycleStatus;
        this.totalCount = totalCount;
        this.version = version;
        this.channel = channel;
        this.productOfferingPrice = productOfferingPrice;
        this.productOfferingRelationship = productOfferingRelationship;
    }

    public List<Channel> getChannel() {
        return channel;
    }

    public void setChannel(List<Channel> channel) {
        this.channel = channel;
    }

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

    public Boolean getIsSellable() {
        return isSellable;
    }

    public void setIsSellable(Boolean isSellable) {
        this.isSellable = isSellable;
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

    public List<ProductOfferingPrice> getProductOfferingPrice() {
        return productOfferingPrice;
    }

    public void setProductOfferingPrice(List<ProductOfferingPrice> productOfferingPrice) {
        this.productOfferingPrice = productOfferingPrice;
    }

    public List<ProductOfferingRelationship> getProductOfferingRelationship() {
        return productOfferingRelationship;
    }

    public void setProductOfferingRelationship(List<ProductOfferingRelationship> productOfferingRelationship) {
        this.productOfferingRelationship = productOfferingRelationship;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isBundle=" + isBundle +
                ", isSellable=" + isSellable +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", lifecycleStatus='" + lifecycleStatus + '\'' +
                ", totalCount=" + totalCount +
                ", version=" + version +
                ", channel=" + channel +
                ", productOfferingPrice=" + productOfferingPrice +
                ", productOfferingRelationship=" + productOfferingRelationship +
                '}';
    }
}
