package com.wordgame.recordsstorage.model;

public class GameRecordMessage {
    private String gameId;
    private String nickname;
    private long score;
    private long createdAt;

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
}
