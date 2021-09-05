package com.wordgame.webui.model;

import java.util.List;
import java.util.Map;

public class GameRecordsResponse {
    private Map<String, List<GameRecord>> gameRecordsByGameMode;

    public GameRecordsResponse() {
    }

    public GameRecordsResponse(Map<String, List<GameRecord>> gameRecordsByGameMode) {
        this.gameRecordsByGameMode = gameRecordsByGameMode;
    }

    public Map<String, List<GameRecord>> getGameRecordsByGameMode() {
        return gameRecordsByGameMode;
    }
}