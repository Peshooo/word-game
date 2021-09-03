package com.wordgame.recordsstorage.service;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGameRecordsMessageHandler implements GameRecordsMessageHandler {
    protected GameRecordsService gameRecordsService;

    @Autowired
    public final void setGameRecordsService(GameRecordsService gameRecordsService) {
        this.gameRecordsService = gameRecordsService;
    }
}
