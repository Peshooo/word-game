package com.wordgame.gameserver.model;

public class GameRecord {
    private String gameId;
    private String nickname;
    private long score;
    private long createdAt;

    public GameRecord(String gameId, String nickname, long score, long createdAt) {
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
}
