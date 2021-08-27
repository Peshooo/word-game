package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.WordMatches;

public class StandardGame extends AbstractGame {
    private static final long INITIAL_TIME_MILLIS = 60_000;

    public StandardGame(String gameId, String nickname) {
        super(gameId, nickname, INITIAL_TIME_MILLIS);
    }

    @Override
    protected void onProcessedWord(WordMatches wordMatches) {
        score += wordMatches.getMatchesCount() * wordMatches.getWord().length();
    }
}
