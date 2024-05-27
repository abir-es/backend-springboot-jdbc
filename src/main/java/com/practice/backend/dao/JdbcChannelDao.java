package com.practice.backend.dao;

import com.practice.backend.entity.Channel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcChannelDao implements ChannelDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcChannelDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Channel> saveChannels(String productId, List<Channel> channels) {
        String insertChannelSql = "INSERT INTO public.channel (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING";
        String insertProductChannelSql = "INSERT INTO public.product_channel (product_id, channel_id) VALUES (?, ?) ON CONFLICT (product_id, channel_id) DO NOTHING";

        List<Channel> savedChannels = new ArrayList<>();

        for (Channel channel : channels) {
            jdbcTemplate.update(insertChannelSql, channel.getId(), channel.getName());
            jdbcTemplate.update(insertProductChannelSql, productId, channel.getId());

            savedChannels.add(channel);
        }

        return savedChannels;
    }

    @Override
    public List<Channel> updateChannels(String productId, List<Channel> channels) {
        String upsertChannelSql = "INSERT INTO public.channel (id, name) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name";
        String deleteProductChannelsSql = "DELETE FROM public.product_channel WHERE product_id = ?";
        String insertProductChannelSql = "INSERT INTO public.product_channel (product_id, channel_id) VALUES (?, ?) ON CONFLICT (product_id, channel_id) DO NOTHING";

        // Delete existing product-channel relationships
        jdbcTemplate.update(deleteProductChannelsSql, productId);

        List<Channel> updatedChannels = new ArrayList<>();

        // Insert/update channels and product-channel relationships
        for (Channel channel : channels) {
            jdbcTemplate.update(upsertChannelSql, channel.getId(), channel.getName());
            jdbcTemplate.update(insertProductChannelSql, productId, channel.getId());

            updatedChannels.add(channel);
        }

        return updatedChannels;
    }

    @Override
    public List<Channel> getChannelsByProductId(String productId) {
        String selectChannelsSql = "SELECT c.id, c.name FROM public.channel c JOIN public.product_channel pc ON c.id = pc.channel_id WHERE pc.product_id = ?";
        return jdbcTemplate.query(selectChannelsSql, new BeanPropertyRowMapper<>(Channel.class), productId);
    }

    @Override
    public void deleteByProductId(String productId){
        String sql = "DELETE FROM public.product_channel WHERE product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
