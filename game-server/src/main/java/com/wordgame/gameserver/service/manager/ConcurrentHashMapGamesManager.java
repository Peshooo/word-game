package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.service.gameplay.Game;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapGamesManager implements GamesManager {
    private final ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        games.put(game.getGameId(), game);
    }

    @Override
    public Game get(String gameId) {
        return games.get(gameId);
    }

    @Override
    public void delete(String gameId) {
        games.remove(gameId);
    }

    @Override
    public Game perform(String gameId, BiFunction<String, Game, Game> operation) {
        return games.computeIfPresent(gameId, operation);
    }
}
