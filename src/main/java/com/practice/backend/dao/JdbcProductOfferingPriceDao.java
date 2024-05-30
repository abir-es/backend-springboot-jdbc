package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingPrice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcProductOfferingPriceDao implements ProductOfferingPriceDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductOfferingPriceDao(DataSource source) {
        jdbcTemplate = new JdbcTemplate(source);
    }

    @Override
    public List<ProductOfferingPrice> saveProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices) {
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

        return productOfferingPrices;
    }

    @Override
    public List<ProductOfferingPrice> updateProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices) {
        String selectSql = "SELECT pop.id FROM public.product_offering_price pop " +
                "JOIN public.product p ON pop.product_id = p.id " +
                "WHERE p.id = ? AND pop.name = ?";
        String insertSql = "INSERT INTO public.product_offering_price (id, product_id, name, description, is_bundle, lifecycle_status, recurring_charge_period_length, price_type, version, last_update, percentage) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE public.product_offering_price SET name = ?, description = ?, is_bundle = ?, lifecycle_status = ?, recurring_charge_period_length = ?, price_type = ?, version = ?, last_update = ?, percentage = ? " +
                "WHERE id = ?";

        List<Object[]> inserts = new ArrayList<>();
        List<Object[]> updates = new ArrayList<>();

        for (ProductOfferingPrice price : productOfferingPrices) {
            // Check if the ProductOfferingPrice exists
            List<String> existingIds = jdbcTemplate.query(selectSql, (rs, rowNum) -> rs.getString("id"), productId, price.getName());

            if (existingIds.isEmpty()) {
                // Prepare for batch insert
                inserts.add(new Object[]{price.getId(), productId, price.getName(), price.getDescription(), price.getIsBundle(),
                        price.getLifecycleStatus(), price.getRecurringChargePeriodLength(), price.getPriceType(),
                        price.getVersion(), price.getLastUpdate(), price.getPercentage()});
            } else {
                // Prepare for batch update
                updates.add(new Object[]{price.getName(), price.getDescription(), price.getIsBundle(), price.getLifecycleStatus(),
                        price.getRecurringChargePeriodLength(), price.getPriceType(), price.getVersion(),
                        price.getLastUpdate(), price.getPercentage(), existingIds.get(0)});
            }
        }

        // Perform batch insert and update
        if (!inserts.isEmpty()) {
            jdbcTemplate.batchUpdate(insertSql, inserts);
        }
        if (!updates.isEmpty()) {
            jdbcTemplate.batchUpdate(updateSql, updates);
        }

        return productOfferingPrices;
    }

    @Override
    public List<ProductOfferingPrice> getProductOfferingPricesByProductId(String productId) {
        String query = "SELECT * FROM public.product_offering_price WHERE product_id = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ProductOfferingPrice.class), productId);
    }

    @Override
    public void deleteByProductId(String productId) {
        String sql = "DELETE FROM public.product_offering_price WHERE product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
