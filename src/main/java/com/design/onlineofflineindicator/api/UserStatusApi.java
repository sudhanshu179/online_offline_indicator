package com.design.onlineofflineindicator.api;

import com.design.onlineofflineindicator.domain.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserStatusApi {

    @Autowired
    CacheManager cacheManager;

    @GetMapping("/api/v1/heartbeat/{userId}")
    public Object heartBeat(@PathVariable("userId") String userId){
        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName))) {
            return ResponseEntity.internalServerError();
        }
        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName).get(userId))) {
            cacheManager.getCache(AppConstants.cacheName).put(userId, 1);
            return ResponseEntity.notFound();
        }
        cacheManager.getCache(AppConstants.cacheName).put(userId,1);
        return ResponseEntity.ok();
    }

//    @PutMapping("/api/v1/heartbeat/{userId}")
//    public Object setHeartBeat(@PathVariable("userId") String userId){
//        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName))) {
//            return ResponseEntity.internalServerError();
//        }
//        if (Objects.isNull(cacheManager.getCache(AppConstants.cacheName).get(userId))) {
//            cacheManager.getCache(AppConstants.cacheName).put(userId, 1);
//            return ResponseEntity.notFound();
//        }
//        cacheManager.getCache(AppConstants.cacheName).put(userId,1);
//        return ResponseEntity.ok();
//    }

    @CachePut(value = AppConstants.cacheName, key = "#userId")
    public int putInCacheWithExpiry(int userId){
       return 1;
    }
}
