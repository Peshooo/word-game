package com.wordgame.gameserver;

import com.wordgame.gameserver.model.reqres.GameStateResponse;
import com.wordgame.gameserver.service.gameplay.Game;

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
