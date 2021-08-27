package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.service.gameplay.StandardGame;

import java.util.function.BiFunction;

public class StandardGamesManager implements GamesManager<StandardGame> {
    @Override
    public void save(StandardGame game) {

    }

    @Override
    public StandardGame get(String gameId) {
        return null;
    }

    @Override
    public void delete(String gameId) {

    }

    @Override
    public StandardGame perform(String gameId, BiFunction<String, StandardGame, StandardGame> operation) {
        return null;
    }
}
