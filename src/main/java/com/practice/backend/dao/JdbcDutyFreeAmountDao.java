package com.practice.backend.dao;

import com.practice.backend.entity.DutyFreeAmount;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcDutyFreeAmountDao implements DutyFreeAmountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDutyFreeAmountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveDutyFreeAmount(DutyFreeAmount dutyFreeAmount) {
        String insertDutyFreeAmountSql = "INSERT INTO public.duty_free_amount (price_id, value, unit) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertDutyFreeAmountSql, dutyFreeAmount.getPriceId(), dutyFreeAmount.getValue(), dutyFreeAmount.getUnit());
    }

    @Override
    public void updateDutyFreeAmount(DutyFreeAmount dutyFreeAmount) {
        String updateDutyFreeAmountSql = "UPDATE public.duty_free_amount SET value = ?, unit = ? WHERE id = ?";
        jdbcTemplate.update(updateDutyFreeAmountSql, dutyFreeAmount.getValue(), dutyFreeAmount.getUnit(), dutyFreeAmount.getId());
    }

    @Override
    public DutyFreeAmount findByPriceId(int priceId) {
        String selectDutyFreeAmountSql = "SELECT * FROM public.duty_free_amount WHERE price_id = ?";
        return jdbcTemplate.queryForObject(selectDutyFreeAmountSql, new BeanPropertyRowMapper<>(DutyFreeAmount.class), priceId);
    }
}
