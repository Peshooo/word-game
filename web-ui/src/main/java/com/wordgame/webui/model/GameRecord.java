package com.wordgame.webui.model;

public class GameRecord {
    private String gameId;
    private String nickname;
    private long score;
    private long createdAt;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public GameRecord(String gameId, String nickname, long score, long createdAt) {
        this.gameId = gameId;
        this.nickname = nickname;
        this.score = score;
        this.createdAt = createdAt;
    }

    public GameRecord() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
