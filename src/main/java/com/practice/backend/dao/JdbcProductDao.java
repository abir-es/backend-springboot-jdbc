package com.practice.backend.dao;

import com.practice.backend.entity.*;
import com.practice.backend.mapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product findById(String id) {
        String query = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new ProductRowMapper(), id);
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM product ORDER BY id";
        return jdbcTemplate.query(query, new ProductRowMapper());
    }

    @Override
    public Product save(Product product) {
        String insertProductSql = "INSERT INTO public.product " +
                "(id, name, description, lifecycle_status, is_sellable, version, last_update, total_count, is_bundle) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertProductSql, product.getId(), product.getName(), product.getDescription(), product.getLifecycleStatus(), product.getIsSellable(), product.getVersion(), product.getLastUpdate(), product.getTotalCount(), product.getIsBundle());

        return product;
    }

    @Override
    public Product update(Product product) {
        String updateProductSql = "UPDATE public.product SET name = ?, description = ?, lifecycle_status = ?, is_sellable = ?, version = ?, last_update = ?, total_count = ?, is_bundle = ? WHERE id = ?";
        jdbcTemplate.update(updateProductSql, product.getName(), product.getDescription(), product.getLifecycleStatus(), product.getIsSellable(), product.getVersion(), product.getLastUpdate(), product.getTotalCount(), product.getIsBundle(), product.getId());
        return product;
    }

    @Override
    public void deleteById(String id) {
        String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
