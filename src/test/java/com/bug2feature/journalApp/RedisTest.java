package com.bug2feature.journalApp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Disabled
    @Test
    void redisTest(){

        redisTemplate.opsForValue().set("email","graapes@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a = 1;

    }
}
