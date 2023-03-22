package com.design.onlineofflineindicator.api;

import com.design.onlineofflineindicator.domain.AppConstants;
import com.design.onlineofflineindicator.event.RedisPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
public class UserStatusApi {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    RedisPublisher redisProducer;
    
//    @Autowired
//    RedisTemplate redisTemplate;

    @GetMapping("/api/v1/user/{userId}/heartbeat")
    public ResponseEntity<Object> heartBeat(@PathVariable("userId") String userId){
        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName))) {
            return ResponseEntity.internalServerError().body(null);
        }
        cacheManager.getCache(AppConstants.cacheName).put(userId,1);
        return ResponseEntity.ok().body(null);
    }

    @CachePut(value = AppConstants.cacheName, key = "#userId")
    public int putInCacheWithExpiry(int userId){
       return 1;
    }

    @GetMapping("/api/v1/user/{userId}/status")
    public ResponseEntity<Object> getUserStatus(@PathVariable("userId") String userId){
        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName))) {
            return ResponseEntity.internalServerError().body(null);
        }
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(AppConstants.cacheName).get(userId);
        if (Objects.isNull(valueWrapper)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(valueWrapper.get());
    }

    @PutMapping("/api/v1/user/{userId}/message/send")
    public ResponseEntity<Object> getUserStatus(@PathVariable("userId") String userId,
                                                @RequestParam("message") String message){
        redisProducer.produce(message+" "+userId);
//        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
//            connection.listCommands().rPop("sdgvdfbf".getBytes());
//            return null;
//        });
        return ResponseEntity.ok().body(null);
    }
}
