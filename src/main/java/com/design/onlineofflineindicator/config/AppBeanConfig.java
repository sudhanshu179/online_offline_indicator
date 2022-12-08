package com.design.onlineofflineindicator.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppBeanConfig {

    @Bean
    public CacheManager getCacheManager(RedisConnectionFactory connectionFactory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().
                entryTtl(Duration.ofSeconds(30));

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("heartbeat", redisCacheConfiguration);
        return RedisCacheManager.RedisCacheManagerBuilder.
                fromConnectionFactory(connectionFactory).
                withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    @Bean
    public RedisConnectionFactory getRedisConnectionFactory(){
        return new LettuceConnectionFactory();
    }
}
