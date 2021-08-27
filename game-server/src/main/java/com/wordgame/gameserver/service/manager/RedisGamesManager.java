package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class RedisGamesManager implements GamesManager {
    private static final String HASH_NAME = "games";

    private final RedisMap<String, RedisGame> redisMap; //TODO: Add expiration for hanging games

    public RedisGamesManager(RedisTemplate<String, Object> redisTemplate) {
        BoundHashOperations<String, String, RedisGame> boundHashOperations = redisTemplate.boundHashOps(HASH_NAME);
        redisMap = new DefaultRedisMap<>(boundHashOperations);
    }

    @Override
    public void save(Game game) {
        RedisGame redisGame = toRedisGame(game);
        redisMap.put(game.getGameId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        RedisGame redisGame = redisMap.get(gameId);

        return toGame(redisGame);
    }

    @Override
    public void delete(String gameId) {
        redisMap.remove(gameId);
    }

    @Override
    public Game perform(String gameId, BiFunction<String, Game, Game> operation) {
        while (true) {
            RedisGame redisGame = redisMap.get(gameId);
            Game game = toGame(redisGame);
            Game result = operation.apply(gameId, game);
            RedisGame redisGameResult = toRedisGame(result);

            if (redisMap.replace(gameId, redisGame, redisGameResult)) {
                return result;
            }
        }
    }


    private RedisGame toRedisGame(Game game) {
        RedisGame redisGame = new RedisGame();
        redisGame.setGameId(game.getGameId());
        redisGame.setNickname(game.getNickname());
        redisGame.setGameMode(game.getGameMode());
        redisGame.setScore(game.getScore());
        redisGame.setGameStatus(game.getGameStatus());
        redisGame.setWords(game.getWords());
        redisGame.setLastUpdateTimestamp(game.getTimeLeftMillis());
        redisGame.setTimeLeftMillis(game.getTimeLeftMillis());

        return redisGame;
    }

    private Game toGame(RedisGame redisGame)
    {
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
