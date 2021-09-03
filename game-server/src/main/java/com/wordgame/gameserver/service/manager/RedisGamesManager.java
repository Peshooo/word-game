package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.service.RedisGameTranslator;
import com.wordgame.gameserver.service.gameplay.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class RedisGamesManager implements GamesManager {
    @Autowired
    private RedisMap<String, RedisGame> redisGamesMap;

    @Autowired
    private RedisGameLockService redisGameLockService;

    @Autowired
    private RedisGamesExpirationService redisGamesExpirationService;

    @Override
    public void save(Game game) {
        setExpiration(game.getGameId());

        RedisGame redisGame = RedisGameTranslator.toRedisGame(game);
        redisGamesMap.put(redisGame.getGameId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        RedisGame redisGame = getRedisGame(gameId);

        return RedisGameTranslator.fromRedisGame(redisGame);
    }

    private RedisGame getRedisGame(String gameId) {
        setExpiration(gameId);

        return redisGamesMap.get(gameId);
    }

    private void setExpiration(String gameId) {
        redisGamesExpirationService.setExpiration(gameId);
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
