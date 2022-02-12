package com.wordgame.webui.game.service.manager;

import com.wordgame.webui.game.model.Game;

import java.util.function.BiFunction;

public interface GamesManager {
    void save(Game game);

    Game get(String gameId);

    void delete(String gameId);

    Game perform(String gameId, BiFunction<String, Game, Game> operation);
}
