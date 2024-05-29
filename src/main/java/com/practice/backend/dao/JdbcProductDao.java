package com.practice.backend.dao;

import com.practice.backend.entity.*;
import com.practice.backend.mapper.ProductListRowMapper;
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
        String query = "SELECT " +
                "p.*, " +
                "c.id AS channel_id, c.name AS channel_name, " +
                "pop.id AS pop_id, pop.name AS pop_name, pop.description AS pop_description, pop.is_bundle AS pop_is_bundle, " +
                "pop.lifecycle_status AS pop_lifecycle_status, pop.recurring_charge_period_length AS pop_recurring_charge_period_length, " +
                "pop.price_type AS pop_price_type, pop.version AS pop_version, pop.last_update AS pop_last_update, pop.percentage AS pop_percentage, " +
                "pr.id AS price_id, pr.percentage AS price_percentage, pr.tax_category AS price_tax_category, pr.tax_rate AS price_tax_rate, " +
                "pr.unit AS price_unit, pr.value AS price_value, " +
                "df.id AS df_id, df.value AS df_value, df.unit AS df_unit, " +
                "ti.id AS ti_id, ti.value AS ti_value, ti.unit AS ti_unit, " +
                "um.id AS um_id, um.amount AS um_amount, um.units AS um_units, " +
                "vf.id AS vf_id, vf.start_date_time AS vf_start_date_time, " +
                "por.id AS por_id, por.name AS por_name, por.role AS por_role, por.type AS por_type " +
                "FROM product p " +
                "LEFT JOIN product_channel pc ON p.id = pc.product_id " +
                "LEFT JOIN channel c ON pc.channel_id = c.id " +
                "LEFT JOIN product_offering_price pop ON p.id = pop.product_id " +
                "LEFT JOIN price pr ON pop.id = pr.product_offering_price_id " +
                "LEFT JOIN duty_free_amount df ON pr.id = df.price_id " +
                "LEFT JOIN tax_included_amount ti ON pr.id = ti.price_id " +
                "LEFT JOIN unit_of_measure um ON pop.id = um.product_offering_price_id " +
                "LEFT JOIN valid_for vf ON pop.id = vf.product_offering_price_id " +
                "LEFT JOIN product_product_offering_relationship ppor ON p.id = ppor.product_id " +
                "LEFT JOIN product_offering_relationship por ON ppor.product_offering_relationship_id = por.id " +
                "WHERE p.id = ?";
        return jdbcTemplate.query(query, new ProductRowMapper(), id);
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT " +
                "p.*, " +
                "c.id AS channel_id, c.name AS channel_name, " +
                "pop.id AS pop_id, pop.name AS pop_name, pop.description AS pop_description, pop.is_bundle AS pop_is_bundle, " +
                "pop.lifecycle_status AS pop_lifecycle_status, pop.recurring_charge_period_length AS pop_recurring_charge_period_length, " +
                "pop.price_type AS pop_price_type, pop.version AS pop_version, pop.last_update AS pop_last_update, pop.percentage AS pop_percentage, " +
                "pr.id AS price_id, pr.percentage AS price_percentage, pr.tax_category AS price_tax_category, pr.tax_rate AS price_tax_rate, " +
                "pr.unit AS price_unit, pr.value AS price_value, " +
                "df.id AS df_id, df.value AS df_value, df.unit AS df_unit, " +
                "ti.id AS ti_id, ti.value AS ti_value, ti.unit AS ti_unit, " +
                "um.id AS um_id, um.amount AS um_amount, um.units AS um_units, " +
                "vf.id AS vf_id, vf.start_date_time AS vf_start_date_time, " +
                "por.id AS por_id, por.name AS por_name, por.role AS por_role, por.type AS por_type " +
                "FROM product p " +
                "LEFT JOIN product_channel pc ON p.id = pc.product_id " +
                "LEFT JOIN channel c ON pc.channel_id = c.id " +
                "LEFT JOIN product_offering_price pop ON p.id = pop.product_id " +
                "LEFT JOIN price pr ON pop.id = pr.product_offering_price_id " +
                "LEFT JOIN duty_free_amount df ON pr.id = df.price_id " +
                "LEFT JOIN tax_included_amount ti ON pr.id = ti.price_id " +
                "LEFT JOIN unit_of_measure um ON pop.id = um.product_offering_price_id " +
                "LEFT JOIN valid_for vf ON pop.id = vf.product_offering_price_id " +
                "LEFT JOIN product_product_offering_relationship ppor ON p.id = ppor.product_id " +
                "LEFT JOIN product_offering_relationship por ON ppor.product_offering_relationship_id = por.id " +
                "ORDER BY p.id";

        return jdbcTemplate.query(query, new ProductListRowMapper());
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
