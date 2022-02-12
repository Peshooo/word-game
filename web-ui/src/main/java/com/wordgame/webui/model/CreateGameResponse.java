package com.wordgame.webui.model;

public class CreateGameResponse {
    private String gameId;

    public CreateGameResponse(String gameId) {
        this.gameId = gameId;
    }

    public CreateGameResponse() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
