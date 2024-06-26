package com.practice.backend.dao;

import com.practice.backend.entity.Price;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcPriceDao implements PriceDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPriceDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Price savePrice(String offeringPriceId, Price price) {
        String insertPriceSql = "INSERT INTO public.price (product_offering_price_id, percentage, tax_category, tax_rate, unit, value) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        int priceId = jdbcTemplate.queryForObject(insertPriceSql, Integer.class, offeringPriceId, price.getPercentage(), price.getTaxCategory(), price.getTaxRate(), price.getUnit(), price.getValue());
        price.setId(priceId);
        return price;
    }

    @Override
    public Price updatePrice(Price price) {
        String updatePriceSql = "UPDATE public.price SET percentage = ?, tax_category = ?, tax_rate = ?, unit = ?, value = ? WHERE id = ?";
        jdbcTemplate.update(updatePriceSql, price.getPercentage(), price.getTaxCategory(), price.getTaxRate(), price.getUnit(), price.getValue(), price.getId());
        return price;
    }

    @Override
    public Price findByProductOfferingPriceId(String productOfferingPriceId) {
        String selectPriceSql = "SELECT * FROM public.price WHERE product_offering_price_id = ?";
        return jdbcTemplate.queryForObject(selectPriceSql, new BeanPropertyRowMapper<>(Price.class), productOfferingPriceId);
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM public.price WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
