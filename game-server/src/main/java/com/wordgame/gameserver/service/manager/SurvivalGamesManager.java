package com.wordgame.gameserver.service.manager;

import com.wordgame.gameserver.service.gameplay.SurvivalGame;

import java.util.function.BiFunction;

public class SurvivalGamesManager implements GamesManager<SurvivalGame> {
    @Override
    public void save(SurvivalGame game) {

    }

    @Override
    public SurvivalGame get(String gameId) {
        return null;
    }

    @Override
    public void delete(String gameId) {

    }

    @Override
    public SurvivalGame perform(String gameId, BiFunction<String, SurvivalGame, SurvivalGame> operation) {
        return null;
    }
}
