package com.wordgame.gameserver.model.reqres;

public class CreateGameResponse {
    private String gameId;

    public CreateGameResponse(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
