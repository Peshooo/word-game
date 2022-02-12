package com.wordgame.webui.game.service;

import com.wordgame.webui.game.model.*;

import java.util.List;
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

    private static String generateGameId() {
        return UUID.randomUUID().toString();
    }

    private static IllegalArgumentException unrecognizedGameModeException() {
        return new IllegalArgumentException(UNRECOGNIZED_GAME_MODE_EXCEPTION_MESSAGE);
    }
}