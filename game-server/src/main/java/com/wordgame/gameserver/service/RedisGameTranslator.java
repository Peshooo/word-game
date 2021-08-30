package com.wordgame.gameserver.service;

import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.service.gameplay.Game;

public class RedisGameTranslator {
    public static RedisGame toRedisGame(Game game) {
        return RedisGame.builder()
                .gameId(game.getGameId())
                .nickname(game.getNickname())
                .gameMode(game.getGameMode())
                .score(game.getScore())
                .gameStatus(game.getGameStatus())
                .words(game.getWords())
                .lastUpdateTimestamp(game.getLastUpdateTimestamp())
                .timeLeftMillis(game.getTimeLeftMillis())
                .build();
    }

    public static Game fromRedisGame(RedisGame redisGame) {
        return GameFactory.create(redisGame);
    }
}
