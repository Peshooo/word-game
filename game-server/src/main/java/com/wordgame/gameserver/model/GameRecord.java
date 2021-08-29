package com.wordgame.gameserver.model;

import java.time.OffsetDateTime;

public class GameRecord {
    private String gameId;
    private String nickname;
    private long score;
    private OffsetDateTime createdAt;

    public GameRecord(String gameId, String nickname, long score, OffsetDateTime createdAt) {
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
