package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.WordMatches;

public class SurvivalGame extends AbstractGame {
    private final long maximumTimeMillis;
    private final long additionalTimeMillisPerCorrectWord;

    public SurvivalGame(String gameId, String nickname, long initialTimeMillis, long maximumTimeMillis, long additionalTimeMillisPerCorrectWord) {
        super(gameId, nickname, Math.min(maximumTimeMillis, initialTimeMillis));
        this.maximumTimeMillis = maximumTimeMillis;
        this.additionalTimeMillisPerCorrectWord = additionalTimeMillisPerCorrectWord;
    }

    @Override
    protected void onProcessedWord(WordMatches wordMatches) {
        score += wordMatches.getMatchesCount() * wordMatches.getWord().length();
        timeLeftMillis = Math.min(maximumTimeMillis, timeLeftMillis + additionalTimeMillisPerCorrectWord * wordMatches.getMatchesCount());
    }
}
