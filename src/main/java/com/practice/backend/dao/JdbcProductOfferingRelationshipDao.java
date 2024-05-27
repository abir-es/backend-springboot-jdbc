package com.practice.backend.dao;

import com.practice.backend.entity.ProductOfferingRelationship;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcProductOfferingRelationshipDao implements ProductOfferingRelationshipDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductOfferingRelationshipDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ProductOfferingRelationship> saveProductOfferingRelationships(String productId, List<ProductOfferingRelationship> relationships) {
        String insertProductOfferingRelationshipSql = "INSERT INTO public.product_offering_relationship (id, name, role, type) VALUES (?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";
        String insertProductProductOfferingRelationshipSql = "INSERT INTO public.product_product_offering_relationship (product_id, product_offering_relationship_id) VALUES (?, ?)";

        List<ProductOfferingRelationship> savedPOR = new ArrayList<>();

        for (ProductOfferingRelationship relationship : relationships) {
            jdbcTemplate.update(insertProductOfferingRelationshipSql, relationship.getId(), relationship.getName(), relationship.getRole(), relationship.getType());
            jdbcTemplate.update(insertProductProductOfferingRelationshipSql, productId, relationship.getId());

            savedPOR.add(relationship);
        }

        return savedPOR;
    }

    @Override
    public List<ProductOfferingRelationship> updateProductOfferingRelationships(String productId, List<ProductOfferingRelationship> relationships) {
        String upsertSql = "INSERT INTO public.product_offering_relationship (id, name, role, type) VALUES (?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET name= EXCLUDED.name, role = EXCLUDED.role, type = EXCLUDED.type";
        String deleteSql = "DELETE FROM public.product_product_offering_relationship WHERE product_id = ?";
        String insertSql = "INSERT INTO public.product_product_offering_relationship (product_id, product_offering_relationship_id) VALUES (?, ?) ON CONFLICT (product_id, product_offering_relationship_id) DO NOTHING";

        jdbcTemplate.update(deleteSql, productId);

        List<ProductOfferingRelationship> updatedPOR = new ArrayList<>();

        for (ProductOfferingRelationship relationship : relationships) {
            jdbcTemplate.update(upsertSql, relationship.getId(), relationship.getName(), relationship.getRole(), relationship.getType());
            jdbcTemplate.update(insertSql, productId, relationship.getId());

            updatedPOR.add(relationship);
        }

        return updatedPOR;
    }

    @Override
    public List<ProductOfferingRelationship> getProductOfferingRelationshipsByProductId(String productId) {
        String selectProductOfferingRelationshipsSql = "SELECT por.id, por.name, por.role, por.type FROM public.product_offering_relationship por JOIN public.product_product_offering_relationship ppor ON por.id = ppor.product_offering_relationship_id WHERE ppor.product_id = ?";
        return jdbcTemplate.query(selectProductOfferingRelationshipsSql, (rs, rowNum) -> new ProductOfferingRelationship(rs.getString("id"), rs.getString("name"), rs.getString("role"), rs.getString("type")), productId);
    }

    @Override
    public void deleteByProductId(String productId) {
        String sql = "DELETE FROM public.product_product_offering_relationship where product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
