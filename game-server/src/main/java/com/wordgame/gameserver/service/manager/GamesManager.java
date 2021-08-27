package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.service.gameplay.AbstractGame;

import java.util.function.BiFunction;

public interface GamesManager<G extends AbstractGame> {
    void save(G game);
    G get(String gameId);
    void delete(String gameId);
    G perform(String gameId, BiFunction<String, G, G> operation);
}
