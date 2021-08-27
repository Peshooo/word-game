package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.GameMode;
import com.wordgame.gameserver.model.GameStatus;
import com.wordgame.gameserver.model.WordMatches;

import java.util.List;

public class StandardGame extends Game {
    private static final long INITIAL_TIME_MILLIS = 60_000;

    public StandardGame(String gameId, String nickname, GameStatus gameStatus, long score, long timeLeftMillis, List<Word> words, long lastUpdateTimestamp) {
        super(GameMode.STANDARD, gameId, nickname, gameStatus, score, timeLeftMillis, words, lastUpdateTimestamp);
    }

    public StandardGame(String gameId, String nickname) {
        super(GameMode.STANDARD, gameId, nickname, INITIAL_TIME_MILLIS);
    }

                               @Override
    protected void onProcessedWord(WordMatches wordMatches) {
        score += wordMatches.getMatchesCount() * wordMatches.getWord().length();
    }
}
