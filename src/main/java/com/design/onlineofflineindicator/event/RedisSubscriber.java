package com.design.onlineofflineindicator.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisSubscriber implements MessageListener {

    public static List<String> messageList = new ArrayList<>();

    @Override
    public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
        messageList.add(message.toString());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("RedisSubscriberImpl: onMessage: {}, pattern: {}", message.toString(), new String(pattern));
    }
}
