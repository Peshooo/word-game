package com.wordgame.recordsstorage.model;

import java.util.Objects;

public class GameRecordMessage {
    private String gameId;
    private String nickname;
    private long score;
    private long createdAt;

    public GameRecordMessage() {
    }

    public GameRecordMessage(String gameId, String nickname, long score, long createdAt) {
        this.gameId = gameId;
        this.nickname = nickname;
        this.score = score;
        this.createdAt = createdAt;
    }

    public String getGameId() {
        return gameId;
    }

    public String getNickname() {
        return nickname;
    }

    public long getScore() {
        return score;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecordMessage that = (GameRecordMessage) o;
        return score == that.score && createdAt == that.createdAt && Objects.equals(gameId, that.gameId) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, nickname, score, createdAt);
    }
}
