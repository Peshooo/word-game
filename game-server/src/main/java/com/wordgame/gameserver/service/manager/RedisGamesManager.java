package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class RedisGamesManager implements GamesManager {
    private static final String HASH_NAME = "games";
    private static final String GAME_LOCK_FORMAT = "%s.game.lock";

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMap<String, RedisGame> redisMap;

    public RedisGamesManager(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        this.redisTemplate = redisTemplate;
        this.redisConnectionFactory = redisConnectionFactory;
        BoundHashOperations<String, String, RedisGame> boundHashOperations = redisTemplate.boundHashOps(HASH_NAME);
        redisMap = new DefaultRedisMap<>(boundHashOperations);
    }

    @Override
    public void save(Game game) {
        RedisGame redisGame = toRedisGame(game);
        redisMap.put(redisGame.getGameId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        RedisGame redisGame = redisMap.get(gameId);

        return toGame(redisGame);
    }

    @Override
    public void delete(String gameId) {
        redisMap.remove(gameId);
        redisTemplate.delete(getGameLockName(gameId));
    }

    @Override
    public Game perform(String gameId, BiFunction<String, Game, Game> operation) {
        RedisAtomicInteger gameLock = new RedisAtomicInteger(getGameLockName(gameId), redisConnectionFactory);

        acquireLock(gameLock);
        try {
            return performInIsolation(gameId, operation);
        } finally {
            releaseLock(gameLock);
        }
    }

    private String getGameLockName(String gameId) {
        return String.format(GAME_LOCK_FORMAT, gameId);
    }

    private void acquireLock(RedisAtomicInteger lock) {
        while (!lock.compareAndSet(0, 1)) {
        }
    }

    private Game performInIsolation(String gameId, BiFunction<String, Game, Game> operation) {
        RedisGame redisGame = redisMap.get(gameId);
        Game game = toGame(redisGame);
        Game result = operation.apply(gameId, game);
        RedisGame redisGameResult = toRedisGame(result);

        return toGame(redisMap.replace(gameId, redisGameResult));
    }

    private void releaseLock(RedisAtomicInteger lock) {
        lock.set(0);
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
