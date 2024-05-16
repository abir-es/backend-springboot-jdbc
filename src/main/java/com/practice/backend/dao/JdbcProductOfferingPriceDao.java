package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingPrice;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcProductOfferingPriceDao implements ProductOfferingPriceDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductOfferingPriceDao(DataSource source) {
        jdbcTemplate = new JdbcTemplate(source);
    }

    @Override
    public void saveProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices) {
        String insertProductOfferingPriceSql = "INSERT INTO public.product_offering_price (id, product_id, name, description, is_bundle, lifecycle_status, recurring_charge_period_length, price_type, version, last_update, percentage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        for (ProductOfferingPrice productOfferingPrice : productOfferingPrices) {
            jdbcTemplate.update(insertProductOfferingPriceSql,
                    productOfferingPrice.getId(),
                    productId,
                    productOfferingPrice.getName(),
                    productOfferingPrice.getDescription(),
                    productOfferingPrice.getIsBundle(),
                    productOfferingPrice.getLifecycleStatus(),
                    productOfferingPrice.getRecurringChargePeriodLength(),
                    productOfferingPrice.getPriceType(),
                    productOfferingPrice.getVersion(),
                    productOfferingPrice.getLastUpdate(),
                    productOfferingPrice.getPercentage()
            );
        }
    }

    @Override
    public void updateProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices) {
        String upsertSql = "INSERT INTO public.product_offering_price (id, product_id, name, description, is_bundle, lifecycle_status, recurring_charge_period_length, price_type, version, last_update, percentage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, is_bundle = EXCLUDED.is_bundle, lifecycle_status = EXCLUDED.lifecycle_status, recurring_charge_period_length = EXCLUDED.recurring_charge_period_length, price_type = EXCLUDED.price_type, version = EXCLUDED.version, last_update = EXCLUDED.last_update, percentage = EXCLUDED.percentage";

        // Perform batch UPSERT operation
        jdbcTemplate.batchUpdate(upsertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ProductOfferingPrice price = productOfferingPrices.get(i);
                ps.setString(1, price.getId());
                ps.setString(2, productId);
                ps.setString(3, price.getName());
                ps.setString(4, price.getDescription());
                ps.setBoolean(5, price.getIsBundle());
                ps.setString(6, price.getLifecycleStatus());
                ps.setInt(7, price.getRecurringChargePeriodLength());
                ps.setString(8, price.getPriceType());
                ps.setString(9, price.getVersion());
                ps.setTimestamp(10, Timestamp.valueOf(price.getLastUpdate()));
                ps.setInt(11, price.getPercentage());
            }

            @Override
            public int getBatchSize() {
                return productOfferingPrices.size();
            }
        });
    }

    @Override
    public List<ProductOfferingPrice> getProductOfferingPricesByProductId(String productId) {
        String query = "SELECT * FROM public.product_offering_price WHERE product_id = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ProductOfferingPrice.class), productId);
    }
}
