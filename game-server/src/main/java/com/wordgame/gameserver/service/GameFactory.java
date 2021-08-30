package com.wordgame.gameserver.service;

import com.wordgame.gameserver.model.GameMode;
import com.wordgame.gameserver.model.RedisGame;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;

import java.util.UUID;

public class GameFactory {
    private static final String UNRECOGNIZED_GAME_MODE_EXCEPTION_MESSAGE = "Unrecognized game mode";

    public static Game create(GameMode gameMode, String nickname) {
        String gameId = generateGameId();

        switch (gameMode) {
            case STANDARD:
                return new StandardGame(gameId, nickname);
            case SURVIVAL:
                return new SurvivalGame(gameId, nickname);
            default:
                throw unrecognizedGameModeException();
        }
    }

    public static Game create(RedisGame redisGame) {
        switch (redisGame.getGameMode()) {
            case STANDARD:
                return new StandardGame(
                        redisGame.getGameId(), redisGame.getNickname(),
                        redisGame.getGameStatus(), redisGame.getScore(),
                        redisGame.getTimeLeftMillis(), redisGame.getWords(),
                        redisGame.getLastUpdateTimestamp());
            case SURVIVAL:
                return new SurvivalGame(
                        redisGame.getGameId(), redisGame.getNickname(),
                        redisGame.getGameStatus(), redisGame.getScore(),
                        redisGame.getTimeLeftMillis(), redisGame.getWords(),
                        redisGame.getLastUpdateTimestamp());
            default:
                throw unrecognizedGameModeException();
        }
    }

    private static String generateGameId() {
        return UUID.randomUUID().toString();
    }

    private static IllegalArgumentException unrecognizedGameModeException() {
        return new IllegalArgumentException(UNRECOGNIZED_GAME_MODE_EXCEPTION_MESSAGE);
    }
}
