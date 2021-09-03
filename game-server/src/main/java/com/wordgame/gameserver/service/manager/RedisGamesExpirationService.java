package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.SerializableDummyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisGamesExpirationService {
    private static final String GAME_EXPIRATION_KEY_FORMAT = "expiration.%s";
    private static final Duration GAME_EXPIRATION = Duration.ofSeconds(10);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setExpiration(String gameId) {
        String key = String.format(GAME_EXPIRATION_KEY_FORMAT, gameId);
        BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(key);
        valueOperations.set(SerializableDummyObject.getInstance());
        valueOperations.expire(GAME_EXPIRATION);
    }
}
