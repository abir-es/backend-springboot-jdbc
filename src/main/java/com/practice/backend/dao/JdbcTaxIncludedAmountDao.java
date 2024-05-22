package com.practice.backend.dao;

import com.practice.backend.entity.TaxIncludedAmount;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTaxIncludedAmountDao implements TaxIncludedAmountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTaxIncludedAmountDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount) {
        String insertTaxIncludedAmountSql = "INSERT INTO public.tax_included_amount (price_id, value, unit) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertTaxIncludedAmountSql, taxIncludedAmount.getPriceId(), taxIncludedAmount.getValue(), taxIncludedAmount.getUnit());
    }

    @Override
    public void updateTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount) {
        String updateTaxIncludedAmountSql = "UPDATE public.tax_included_amount SET value = ?, unit = ? WHERE id = ?";
        jdbcTemplate.update(updateTaxIncludedAmountSql, taxIncludedAmount.getValue(), taxIncludedAmount.getUnit(), taxIncludedAmount.getId());
    }

    @Override
    public TaxIncludedAmount findByPriceId(int priceId) {
        String selectTaxIncludedAmountSql = "SELECT * FROM public.tax_included_amount WHERE price_id = ?";
        return jdbcTemplate.queryForObject(selectTaxIncludedAmountSql, new BeanPropertyRowMapper<>(TaxIncludedAmount.class), priceId);
    }

    @Override
    public void deleteByPriceId(int priceId) {
        String sql = "DELETE FROM public.tax_included_amount WHERE price_id = ?";
        jdbcTemplate.update(sql, priceId);
    }
}
