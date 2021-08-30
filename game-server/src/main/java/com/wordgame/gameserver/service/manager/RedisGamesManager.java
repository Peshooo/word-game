package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.model.SerializableDummyObject;
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

        RedisGame redisGame = toRedisGame(game);
        redisMap.put(redisGame.getGameId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        RedisGame redisGame = getRedisGame(gameId);

        return toGame(redisGame);
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
        RedisGame redisGame = getRedisGame(gameId);
        Game game = toGame(redisGame);
        Game result = operation.apply(gameId, game);
        RedisGame redisGameResult = toRedisGame(result);
        redisMap.replace(gameId, redisGameResult);

        return toGame(redisGameResult);
    }

    private RedisGame toRedisGame(Game game) {
        RedisGame redisGame = new RedisGame();
        redisGame.setGameId(game.getGameId());
        redisGame.setNickname(game.getNickname());
        redisGame.setGameMode(game.getGameMode());
        redisGame.setScore(game.getScore());
        redisGame.setGameStatus(game.getGameStatus());
        redisGame.setWords(game.getWords());
        redisGame.setLastUpdateTimestamp(game.getLastUpdateTimestamp());
        redisGame.setTimeLeftMillis(game.getTimeLeftMillis());

        return redisGame;
    }

    private Game toGame(RedisGame redisGame) {
        switch (redisGame.getGameMode()) {
            case STANDARD:
                return new StandardGame(redisGame.getGameId(), redisGame.getNickname(), redisGame.getGameStatus(), redisGame.getScore(), redisGame.getTimeLeftMillis(), redisGame.getWords(), redisGame.getLastUpdateTimestamp());
            case SURVIVAL:
                return new SurvivalGame(redisGame.getGameId(), redisGame.getNickname(), redisGame.getGameStatus(), redisGame.getScore(), redisGame.getTimeLeftMillis(), redisGame.getWords(), redisGame.getLastUpdateTimestamp());
            default:
                throw new RuntimeException("Unrecognized game mode"); //TODO: Replace both runtime exceptions, add exception handlers in all projects
        }
    }
}
