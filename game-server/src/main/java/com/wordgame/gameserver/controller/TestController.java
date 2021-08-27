package com.wordgame.gameserver.controller;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final RedisTemplate<String, Object> redisTemplate;

    public TestController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/{key}/{value}")
    public void save(@PathVariable String key, @PathVariable String value) {
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps("h");
        boundHashOperations.put(key, value);
        System.out.println("Get retrieves " + boundHashOperations.get(key));
    }

    @GetMapping("/{key}")
    public String get(@PathVariable String key) {
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps("h");
        return boundHashOperations.get(key);
    }
}
