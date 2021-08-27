package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.WordMatches;

public class StandardGame extends AbstractGame {
    public StandardGame(String gameId, String nickname, long initialTimeMillis) {
        super(gameId, nickname, initialTimeMillis);
    }

    @Override
    protected void onProcessedWord(WordMatches wordMatches) {
        score += wordMatches.getMatchesCount() * wordMatches.getWord().length();
    }
}
