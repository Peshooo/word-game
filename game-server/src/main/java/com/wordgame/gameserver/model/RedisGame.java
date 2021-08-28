package com.wordgame.gameserver.model;

import com.wordgame.gameserver.service.gameplay.Word;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RedisGame implements Serializable {
    private String gameId;
    private String nickname;
    private GameStatus gameStatus;
    private long score;
    private long lastUpdateTimestamp;
    private long timeLeftMillis;
    private List<Word> words;
    private GameMode gameMode;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public long getTimeLeftMillis() {
        return timeLeftMillis;
    }

    public void setTimeLeftMillis(long timeLeftMillis) {
        this.timeLeftMillis = timeLeftMillis;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisGame redisGame = (RedisGame) o;
        return score == redisGame.score && lastUpdateTimestamp == redisGame.lastUpdateTimestamp && timeLeftMillis == redisGame.timeLeftMillis && Objects.equals(gameId, redisGame.gameId) && Objects.equals(nickname, redisGame.nickname) && gameStatus == redisGame.gameStatus && Objects.equals(words, redisGame.words) && gameMode == redisGame.gameMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, nickname, gameStatus, score, lastUpdateTimestamp, timeLeftMillis, words, gameMode);
    }
}
