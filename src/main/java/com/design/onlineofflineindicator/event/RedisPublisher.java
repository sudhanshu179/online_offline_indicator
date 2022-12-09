package com.design.onlineofflineindicator.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisPublisher {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ChannelTopic topic;

    public void produce(String message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
