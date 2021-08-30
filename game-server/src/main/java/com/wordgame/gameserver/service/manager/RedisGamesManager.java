package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.model.SerializableDummyObject;
import com.wordgame.gameserver.service.RedisGameTranslator;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.BiFunction;

@Service
public class RedisGamesManager implements GamesManager {
    private static final String HASH_NAME = "games";

    private static final String GAME_EXPIRATION_KEY_FORMAT = "expiration.%s";
    private static final Duration GAME_EXPIRATION = Duration.ofSeconds(10);

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMap<String, RedisGame> redisMap;

    @Autowired
    private RedisGameLockService redisGameLockService;

    public RedisGamesManager(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        this.redisTemplate = redisTemplate;
        BoundHashOperations<String, String, RedisGame> boundHashOperations = redisTemplate.boundHashOps(HASH_NAME);
        redisMap = new DefaultRedisMap<>(boundHashOperations);
    }

    @Override
    public void save(Game game) {
        String key = String.format(GAME_EXPIRATION_KEY_FORMAT, game.getGameId());
        BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(key);
        valueOperations.set(SerializableDummyObject.getInstance());
        valueOperations.expire(GAME_EXPIRATION);

        RedisGame redisGame = RedisGameTranslator.toRedisGame(game);
        redisMap.put(redisGame.getGameId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        RedisGame redisGame = getRedisGame(gameId);

        return RedisGameTranslator.fromRedisGame(redisGame);
    }

    private RedisGame getRedisGame(String gameId) {
        updateExpiration(gameId);

        return redisMap.get(gameId);
    }

    private void updateExpiration(String gameId) {
        String key = String.format(GAME_EXPIRATION_KEY_FORMAT, gameId);
        BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(key);
        valueOperations.expire(GAME_EXPIRATION);
    }

    @Override
    public void delete(String gameId) {
        redisMap.remove(gameId);
    }

    @Override
    public Game perform(String gameId, BiFunction<String, Game, Game> operation) {
        redisGameLockService.acquireLock(gameId);

        try {
            return performInIsolation(gameId, operation);
        } finally {
            redisGameLockService.releaseLock(gameId);
        }
    }

    private Game performInIsolation(String gameId, BiFunction<String, Game, Game> operation) {
        Game game = get(gameId);
        Game result = operation.apply(gameId, game);
        replace(result);

        return result;
    }

    private void replace(Game game) {
        RedisGame redisGameResult = RedisGameTranslator.toRedisGame(game);
        redisMap.replace(game.getGameId(), redisGameResult);
    }
}
