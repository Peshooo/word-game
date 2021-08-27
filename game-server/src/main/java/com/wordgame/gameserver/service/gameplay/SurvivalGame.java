package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.WordMatches;

public class SurvivalGame extends AbstractGame {
    private static final long INITIAL_TIME_MILLIS = 10_000;
    private static final long MAXIMUM_TIME_MILLIS = 10_000;
    private static final long ADDITIONAL_TIME_MILLIS_PER_CORRECT_WORD = 1_000;

    public SurvivalGame(String gameId, String nickname) {
        super(gameId, nickname, Math.min(MAXIMUM_TIME_MILLIS, INITIAL_TIME_MILLIS));
    }

    @Override
    protected void onProcessedWord(WordMatches wordMatches) {
        score += wordMatches.getMatchesCount() * wordMatches.getWord().length();
        timeLeftMillis = Math.min(MAXIMUM_TIME_MILLIS, timeLeftMillis + ADDITIONAL_TIME_MILLIS_PER_CORRECT_WORD * wordMatches.getMatchesCount());
    }
}
