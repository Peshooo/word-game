package com.wordgame.recordsstorage.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class GameRecord {
    private String gameId;
    private String nickname;
    private Long score;
    private OffsetDateTime createdAt;

    public GameRecord() {
    }

    public GameRecord(String gameId, String nickname, Long score, OffsetDateTime createdAt) {
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

    public Long getScore() {
        return score;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord that = (GameRecord) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(nickname, that.nickname) && Objects.equals(score, that.score) && createdAt.isEqual(that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, nickname, score, createdAt.toInstant().toEpochMilli());
    }
}
