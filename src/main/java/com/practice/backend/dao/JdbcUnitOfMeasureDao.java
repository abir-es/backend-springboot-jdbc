package com.practice.backend.dao;

import com.practice.backend.entity.UnitOfMeasure;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcUnitOfMeasureDao implements UnitOfMeasureDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUnitOfMeasureDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        String insertUnitOfMeasureSql = "INSERT INTO public.unit_of_measure (product_offering_price_id, amount, units) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertUnitOfMeasureSql, unitOfMeasure.getProductOfferingPriceId(), unitOfMeasure.getAmount(), unitOfMeasure.getUnits());
    }

    @Override
    public void updateUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        String updateUnitOfMeasureSql = "UPDATE public.unit_of_measure SET amount = ?, units = ? WHERE id = ?";
        jdbcTemplate.update(updateUnitOfMeasureSql, unitOfMeasure.getAmount(), unitOfMeasure.getUnits(), unitOfMeasure.getId());
    }

    @Override
    public UnitOfMeasure findByProductOfferingPriceId(String productOfferingPriceId) {
        String selectUnitOfMeasureSql = "SELECT * FROM public.unit_of_measure WHERE product_offering_price_id = ?";
        return jdbcTemplate.queryForObject(selectUnitOfMeasureSql, new BeanPropertyRowMapper<>(UnitOfMeasure.class), productOfferingPriceId);
    }

    @Override
    public void deleteByProductOfferingPriceId(String productOfferingPriceId) {
        String sql = "DELETE FROM public.unit_of_measure WHERE product_offering_price_id = ?";
        jdbcTemplate.update(sql, productOfferingPriceId);
    }
}
