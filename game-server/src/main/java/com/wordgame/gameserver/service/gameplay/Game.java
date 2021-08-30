package com.wordgame.gameserver.service.gameplay;

import com.wordgame.gameserver.model.GameMode;
import com.wordgame.gameserver.model.GameStatus;
import com.wordgame.gameserver.model.WordMatches;
import com.wordgame.gameserver.util.WordFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Game {
    protected static final int ACTIVE_WORDS = 10;
    protected static final int CANVAS_WIDTH = 1200;
    protected static final int CANVAS_HEIGHT = 600;

    protected GameMode gameMode;
    protected String gameId;
    protected String nickname;
    protected GameStatus gameStatus;
    protected long score;
    protected long timeLeftMillis;
    protected final List<Word> words;
    protected long lastUpdateTimestamp;

    public Game(GameMode gameMode, String gameId, String nickname, GameStatus gameStatus, long score, long timeLeftMillis, List<Word> words, long lastUpdateTimestamp) {
        this.gameMode = gameMode;
        this.gameId = gameId;
        this.nickname = nickname;
        this.gameStatus = gameStatus;
        this.score = score;
        this.timeLeftMillis = timeLeftMillis;
        this.words = words;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public Game(GameMode gameMode, String gameId, String nickname, long initialTimeMillis) {
        this.gameMode = gameMode;
        this.gameId = gameId;
        this.nickname = nickname;

        gameStatus = GameStatus.NOT_STARTED;
        score = 0;
        timeLeftMillis = initialTimeMillis;
        words = new ArrayList<>();
        refillWords();
        lastUpdateTimestamp = System.currentTimeMillis();
    }

    public void enterWord(String word) {
        if (gameStatus == GameStatus.FINISHED) {
            return;
        }

        startGameIfNotStarted();
        WordMatches wordMatches = processEnteredWord(word);
        onProcessedWord(wordMatches);
        refillWords();
    }

    private void startGameIfNotStarted() {
        if (gameStatus == GameStatus.NOT_STARTED) {
            startGame();
        }
    }

    private void startGame() {
        gameStatus = GameStatus.RUNNING;
    }

    protected WordMatches processEnteredWord(String word) {
        Predicate<Word> wordMatches = w -> w.getWord().equals(word);

        long count = words.stream()
                .filter(wordMatches)
                .count();

        words.removeIf(wordMatches);

        return new WordMatches(count, word);
    }

    protected abstract void onProcessedWord(WordMatches wordMatches);

    public void updateGame() {
        if (gameStatus == GameStatus.FINISHED) {
            return;
        }

        long timestamp = System.currentTimeMillis();

        move(timestamp);
        updateTimeIfRunning(timestamp);
        finishGameIfNoTimeLeft();

        lastUpdateTimestamp = timestamp;
    }

    private void move(long timestamp) {
        words.forEach(word -> word.move(timestamp));
        words.removeIf(this::notOnCanvas);
        refillWords();
    }

    private boolean notOnCanvas(Word word) {
        if (word.getPosition().getX() < 0 || word.getPosition().getY() < 0) {
            return true;
        }

        return word.getPosition().getX() + word.getSize().getWidth() > CANVAS_WIDTH
                || word.getPosition().getY() + word.getSize().getHeight() > CANVAS_HEIGHT;
    }

    private void refillWords() {
        long timestamp = System.currentTimeMillis();

        while (words.size() < ACTIVE_WORDS) {
            words.add(WordFactory.create(timestamp));
        }
    }

    private void updateTimeIfRunning(long timestamp) {
        if (gameStatus == GameStatus.RUNNING) {
            updateTime(timestamp);
        }
    }

    private void updateTime(long timestamp) {
        long millisecondsSinceLastUpdate = timestamp - lastUpdateTimestamp;
        timeLeftMillis -= millisecondsSinceLastUpdate;
    }

    private void finishGameIfNoTimeLeft() {
        if (timeLeftMillis <= 0) {
            finishGame();
        }
    }

    private void finishGame() {
        timeLeftMillis = 0;
        gameStatus = GameStatus.FINISHED;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getGameId() {
        return gameId;
    }

    public String getNickname() {
        return nickname;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public long getScore() {
        return score;
    }

    public long getTimeLeftMillis() {
        return timeLeftMillis;
    }

    public List<Word> getWords() {
        return words;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }
}
