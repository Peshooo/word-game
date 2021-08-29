package com.wordgame.gameserver.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisGameLockService {
    private static final String GAME_LOCK_FORMAT = "%s.game.lock";

    private static final Duration LOCK_EXPIRATION = Duration.ofSeconds(60);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void acquireLock(String gameId) {
        RedisAtomicInteger lock = getGameLock(gameId);

        while (!lock.compareAndSet(0, 1)) {
        }
    }

    public void releaseLock(String gameId) {
        RedisAtomicInteger lock = getGameLock(gameId);
        lock.set(0);
    }

    private RedisAtomicInteger getGameLock(String gameId) {
        String lockName = getGameLockName(gameId);
        RedisAtomicInteger lock = new RedisAtomicInteger(getGameLockName(gameId), redisConnectionFactory);
        redisTemplate.expire(lockName, LOCK_EXPIRATION);

        return lock;
    }

    private String getGameLockName(String gameId) {
        return String.format(GAME_LOCK_FORMAT, gameId);
    }
}
