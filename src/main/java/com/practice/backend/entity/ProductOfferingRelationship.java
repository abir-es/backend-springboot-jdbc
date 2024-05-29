
package com.practice.backend.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductOfferingRelationship {
    private String id;
    private String name;
    private String role;
    @JsonAlias("@type")
    private String type;

    public ProductOfferingRelationship() {
    }

    public ProductOfferingRelationship(String id, String name, String role, String type) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductOfferingRelationship{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
