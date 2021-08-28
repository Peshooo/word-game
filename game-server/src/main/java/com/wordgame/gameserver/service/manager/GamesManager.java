package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.service.gameplay.Game;

import java.util.function.BiFunction;

public interface GamesManager {
    void save(Game game);
    Game get(String gameId);
    void delete(String gameId);
    Game perform(String gameId, BiFunction<String, Game, Game> operation);
}
