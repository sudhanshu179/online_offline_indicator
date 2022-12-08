package com.design.onlineofflineindicator.api;

import com.design.onlineofflineindicator.domain.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
public class UserStatusApi {

    @Autowired
    CacheManager cacheManager;

    @GetMapping("/api/v1/heartbeat/{userId}")
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

    @GetMapping("/api/v1/usrStatus/{userId}")
    public ResponseEntity<Object> getUserStatus(@PathVariable("userId") String userId){
        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName))) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName).get(userId))) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(null);
    }
}
