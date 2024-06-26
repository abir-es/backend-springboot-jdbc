package com.practice.backend.dao;

import com.practice.backend.entity.ValidFor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcValidForDao implements ValidForDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcValidForDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ValidFor saveValidFor(ValidFor validFor) {
        String insertValidForSql = "INSERT INTO public.valid_for (product_offering_price_id, start_date_time) VALUES (?, ?)";
        jdbcTemplate.update(insertValidForSql, validFor.getProductOfferingPriceId(), validFor.getStartDateTime());

        return validFor;
    }

    @Override
    public ValidFor updateValidFor(ValidFor validFor) {
        String updateValidForSql = "UPDATE public.valid_for SET start_date_time = ? WHERE id = ?";
        jdbcTemplate.update(updateValidForSql, validFor.getStartDateTime(), validFor.getId());

        return validFor;
    }

    @Override
    public ValidFor findByProductOfferingPriceId(String productOfferingPriceId) {
        String selectValidForSql = "SELECT * FROM public.valid_for WHERE product_offering_price_id = ?";
        return jdbcTemplate.queryForObject(selectValidForSql, new BeanPropertyRowMapper<>(ValidFor.class), productOfferingPriceId);
    }

    @Override
    public void deleteByProductOfferingPriceId(String productOfferingPriceId) {
        String sql = "DELETE FROM public.valid_for WHERE product_offering_price_id = ?";
        jdbcTemplate.update(sql, productOfferingPriceId);
    }
}
