package com.bug2feature.journalApp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> weatherResponseClass){
        Object o =redisTemplate.opsForValue().get(key);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(o.toString(), weatherResponseClass);
        }
        catch (Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    public void set(String key, Object o , Long ttl){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.SECONDS);
        }
        catch (Exception e){
            log.error("Error:",e);
        }
    }

}
