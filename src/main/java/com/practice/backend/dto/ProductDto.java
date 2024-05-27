
package com.practice.backend.dto;

import java.util.List;

public class ProductDto {
    private String id;
    private String name;
    private String description;
    private Boolean isBundle;
    private Boolean isSellable;
    private String lastUpdate;
    private String lifecycleStatus;
    private int totalCount;
    private int version;
    private List<ChannelDto> channelDto;
    private List<ProductOfferingPriceDto> productOfferingPriceDto;
    private List<ProductOfferingRelationshipDto> productOfferingRelationshipDto;

    public ProductDto() {
    }

    public ProductDto(String id, String name, String description, Boolean isBundle, Boolean isSellable, String lastUpdate, String lifecycleStatus, int totalCount, int version, List<ChannelDto> channelDto, List<ProductOfferingPriceDto> productOfferingPriceDto, List<ProductOfferingRelationshipDto> productOfferingRelationshipDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isBundle = isBundle;
        this.isSellable = isSellable;
        this.lastUpdate = lastUpdate;
        this.lifecycleStatus = lifecycleStatus;
        this.totalCount = totalCount;
        this.version = version;
        this.channelDto = channelDto;
        this.productOfferingPriceDto = productOfferingPriceDto;
        this.productOfferingRelationshipDto = productOfferingRelationshipDto;
    }

    public List<ChannelDto> getChannel() {
        return channelDto;
    }

    public void setChannel(List<ChannelDto> channelDto) {
        this.channelDto = channelDto;
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

    public List<ProductOfferingPriceDto> getProductOfferingPrice() {
        return productOfferingPriceDto;
    }

    public void setProductOfferingPrice(List<ProductOfferingPriceDto> productOfferingPriceDto) {
        this.productOfferingPriceDto = productOfferingPriceDto;
    }

    public List<ProductOfferingRelationshipDto> getProductOfferingRelationship() {
        return productOfferingRelationshipDto;
    }

    public void setProductOfferingRelationship(List<ProductOfferingRelationshipDto> productOfferingRelationshipDto) {
        this.productOfferingRelationshipDto = productOfferingRelationshipDto;
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
                "id:'" + id + '\'' +
                ", name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", isBundle:" + isBundle +
                ", isSellable:" + isSellable +
                ", lastUpdate:'" + lastUpdate + '\'' +
                ", lifecycleStatus:'" + lifecycleStatus + '\'' +
                ", totalCount:" + totalCount +
                ", version:" + version +
                ", channel:" + channelDto +
                ", productOfferingPrice:" + productOfferingPriceDto +
                ", productOfferingRelationship:" + productOfferingRelationshipDto +
                '}';
    }
}
