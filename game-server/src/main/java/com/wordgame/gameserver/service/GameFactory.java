package com.wordgame.gameserver.service;

import com.wordgame.gameserver.model.GameMode;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;

import java.util.UUID;

public class GameFactory {
    public static Game create(GameMode gameMode, String nickname) {
        String gameId = generateGameId();

        switch (gameMode) {
            case STANDARD:
                return new StandardGame(gameId, nickname);
            case SURVIVAL:
                return new SurvivalGame(gameId, nickname);
            default:
                throw new IllegalArgumentException("Unrecognized game mode");
        }
    }

    private static String generateGameId() {
        return UUID.randomUUID().toString();
    }
}
