package com.practice.backend.mapper;

import com.practice.backend.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setLifecycleStatus(rs.getString("lifecycle_status"));
        product.setIsSellable(rs.getBoolean("is_sellable"));
        product.setVersion(rs.getInt("version"));
        product.setLastUpdate(rs.getString("last_update"));
        product.setTotalCount(rs.getInt("total_count"));
        product.setIsBundle(rs.getBoolean("is_bundle"));
        return product;
    }
}
