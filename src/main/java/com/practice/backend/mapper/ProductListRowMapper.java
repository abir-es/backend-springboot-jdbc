package com.practice.backend.mapper;

import com.practice.backend.entity.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListRowMapper implements ResultSetExtractor<List<Product>> {
    @Override
    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Product> productMap = new HashMap<>();
        Map<String, Channel> channelMap = new HashMap<>();
        Map<String, ProductOfferingPrice> popMap = new HashMap<>();
        Map<Integer, Price> priceMap = new HashMap<>();
        Map<String, ProductOfferingRelationship> porMap = new HashMap<>();

        while (rs.next()) {
            String productId = rs.getString("id");
            Product product = productMap.get(productId);

            if (product == null) {
                product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setLifecycleStatus(rs.getString("lifecycle_status"));
                product.setIsSellable(rs.getBoolean("is_sellable"));
                product.setVersion(rs.getInt("version"));
                product.setLastUpdate(rs.getString("last_update"));
                product.setTotalCount(rs.getInt("total_count"));
                product.setIsBundle(rs.getBoolean("is_bundle"));

                product.setChannel(new ArrayList<>());
                product.setProductOfferingPrice(new ArrayList<>());
                product.setProductOfferingRelationship(new ArrayList<>());
                productMap.put(productId, product);
            }

            // Map channels
            String channelId = rs.getString("channel_id");
            if (channelId != null && !channelMap.containsKey(channelId)) {
                Channel channel = new Channel();
                channel.setId(channelId);
                channel.setName(rs.getString("channel_name"));
                channelMap.put(channelId, channel);
                product.getChannel().add(channel);
            }

            // Map product offering prices
            String popId = rs.getString("pop_id");
            if (popId != null && !popMap.containsKey(popId)) {
                ProductOfferingPrice pop = new ProductOfferingPrice();
                pop.setId(popId);
                pop.setName(rs.getString("pop_name"));
                pop.setDescription(rs.getString("pop_description"));
                pop.setIsBundle(rs.getBoolean("pop_is_bundle"));
                pop.setLifecycleStatus(rs.getString("pop_lifecycle_status"));
                pop.setRecurringChargePeriodLength(rs.getInt("pop_recurring_charge_period_length"));
                pop.setPriceType(rs.getString("pop_price_type"));
                pop.setVersion(rs.getString("pop_version"));
                pop.setLastUpdate(rs.getString("pop_last_update"));
                pop.setPercentage(rs.getInt("pop_percentage"));

                // Map nested price
                int priceId = rs.getInt("price_id");
                if (priceId != 0 && !priceMap.containsKey(priceId)) {
                    Price price = new Price();
                    price.setId(priceId);
                    price.setPercentage(rs.getInt("price_percentage"));
                    price.setTaxCategory(rs.getString("price_tax_category"));
                    price.setTaxRate(rs.getInt("price_tax_rate"));
                    price.setUnit(rs.getString("price_unit"));
                    price.setValue(rs.getDouble("price_value"));

                    // Map duty free amount
                    int dfId = rs.getInt("df_id");
                    if (dfId != 0) {
                        DutyFreeAmount df = new DutyFreeAmount();
                        df.setId(dfId);
                        df.setValue(rs.getDouble("df_value"));
                        df.setUnit(rs.getString("df_unit"));
                        price.setDutyFreeAmount(df);
                    }

                    // Map tax included amount
                    int tiId = rs.getInt("ti_id");
                    if (tiId != 0) {
                        TaxIncludedAmount ti = new TaxIncludedAmount();
                        ti.setId(tiId);
                        ti.setValue(rs.getDouble("ti_value"));
                        ti.setUnit(rs.getString("ti_unit"));
                        price.setTaxIncludedAmount(ti);
                    }

                    priceMap.put(priceId, price);
                }

                if (priceId != 0) {
                    pop.setPrice(priceMap.get(priceId));
                }

                // Map unit of measure
                int umId = rs.getInt("um_id");
                if (umId != 0) {
                    UnitOfMeasure um = new UnitOfMeasure();
                    um.setId(umId);
                    um.setAmount(rs.getDouble("um_amount"));
                    um.setUnits(rs.getString("um_units"));
                    pop.setUnitOfMeasure(um);
                }

                // Map valid for
                int vfId = rs.getInt("vf_id");
                if (vfId != 0) {
                    ValidFor vf = new ValidFor();
                    vf.setId(vfId);
                    vf.setStartDateTime(rs.getString("vf_start_date_time"));
                    pop.setValidFor(vf);
                }

                popMap.put(popId, pop);
                product.getProductOfferingPrice().add(pop);
            }

            // Map product offering relationships
            String porId = rs.getString("por_id");
            if (porId != null && !porMap.containsKey(porId)) {
                ProductOfferingRelationship por = new ProductOfferingRelationship();
                por.setId(porId);
                por.setName(rs.getString("por_name"));
                por.setRole(rs.getString("por_role"));
                por.setType(rs.getString("por_type"));
                porMap.put(porId, por);
                product.getProductOfferingRelationship().add(por);
            }
        }

        return new ArrayList<>(productMap.values());
    }
}
