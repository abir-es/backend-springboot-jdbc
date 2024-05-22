package com.practice.backend.dao;

import com.practice.backend.entity.Channel;

import java.util.List;

public interface ChannelDao {
    void saveChannels(String productId, List<Channel> channels);
    void updateChannels(String productId, List<Channel> channels);
    List<Channel> getChannelsByProductId(String productId);
    void deleteByProductId(String productId);
}
