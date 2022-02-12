package com.wordgame.webui.game.util;

import com.wordgame.webui.game.model.Game;
import com.wordgame.webui.game.model.GameStateResponse;

public class GameStateResponseTranslator {
    public static GameStateResponse translate(Game game) {
        return GameStateResponse.builder()
                .nickname(game.getNickname())
                .status(game.getGameStatus())
                .score(game.getScore())
                .timeLeftMillis(game.getTimeLeftMillis())
                .words(game.getWords())
                .build();
    }
}