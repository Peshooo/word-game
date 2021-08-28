package com.wordgame.webui.model;

import java.util.List;

public class GameRecordsResponse {
    private List<GameRecord> standardGameRecords;
    private List<GameRecord> survivalGameRecords;

    public GameRecordsResponse(List<GameRecord> standardGameRecords, List<GameRecord> survivalGameRecords) {
        this.standardGameRecords = standardGameRecords;
        this.survivalGameRecords = survivalGameRecords;
    }

    public List<GameRecord> getStandardGameRecords() {
        return standardGameRecords;
    }

    public List<GameRecord> getSurvivalGameRecords() {
        return survivalGameRecords;
    }
}