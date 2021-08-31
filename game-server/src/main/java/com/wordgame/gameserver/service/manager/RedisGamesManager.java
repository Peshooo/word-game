package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.model.SerializableDummyObject;
import com.wordgame.gameserver.service.RedisGameTranslator;
import com.wordgame.gameserver.service.gameplay.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.BiFunction;

@Service
public class RedisGamesManager implements GamesManager {
    private static final String GAME_EXPIRATION_KEY_FORMAT = "expiration.%s";
    private static final Duration GAME_EXPIRATION = Duration.ofSeconds(10);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisMap<String, RedisGame> redisGamesMap;

    @Autowired
    private RedisGameLockService redisGameLockService;

    @Override
    public void save(Game game) {
        String key = String.format(GAME_EXPIRATION_KEY_FORMAT, game.getGameId());
        BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(key);
        valueOperations.set(SerializableDummyObject.getInstance());
        valueOperations.expire(GAME_EXPIRATION);

        RedisGame redisGame = RedisGameTranslator.toRedisGame(game);
        redisGamesMap.put(redisGame.getGameId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        RedisGame redisGame = getRedisGame(gameId);

        return RedisGameTranslator.fromRedisGame(redisGame);
    }

    private RedisGame getRedisGame(String gameId) {
        updateExpiration(gameId);

        return redisGamesMap.get(gameId);
    }

    private void updateExpiration(String gameId) {
        String key = String.format(GAME_EXPIRATION_KEY_FORMAT, gameId);
        BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(key);
        valueOperations.expire(GAME_EXPIRATION);
    }

    @Override
    public void delete(String gameId) {
        redisGamesMap.remove(gameId);
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
        redisGamesMap.replace(game.getGameId(), redisGameResult);
    }
}
