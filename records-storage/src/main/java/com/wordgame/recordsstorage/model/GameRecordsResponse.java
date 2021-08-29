package com.wordgame.recordsstorage.model;

import java.util.List;

public class GameRecordsResponse {
    private List<? extends GameRecord> standardGameRecords;
    private List<? extends GameRecord> survivalGameRecords;

    public GameRecordsResponse(List<? extends GameRecord> standardGameRecords, List<? extends GameRecord> survivalGameRecords) {
        this.standardGameRecords = standardGameRecords;
        this.survivalGameRecords = survivalGameRecords;
    }

    public List<? extends GameRecord> getStandardGameRecords() {
        return standardGameRecords;
    }

    public List<? extends GameRecord> getSurvivalGameRecords() {
        return survivalGameRecords;
    }
}
