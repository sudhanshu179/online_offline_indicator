package com.design.onlineofflineindicator.config;

import com.design.onlineofflineindicator.event.RedisSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("chatQueue");
    }

    @Bean
    public MessageListenerAdapter getMessageListener(){
        return new MessageListenerAdapter(new RedisSubscriber());
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(getMessageListener(), channelTopic());
//        container.addMessageListener(getMessageListener2(), channelTopic());
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
