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

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void setCreatedAt(long createdAt) {
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
        GameRecordMessage message = (GameRecordMessage) o;
        return score == message.score && createdAt == message.createdAt && Objects.equals(gameId, message.gameId) && Objects.equals(nickname, message.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, nickname, score, createdAt);
    }
}
