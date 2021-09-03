package com.wordgame.recordsstorage.service;

import com.wordgame.recordsstorage.model.GameRecordMessage;

public interface GameRecordsMessageHandler {
    void handle(GameRecordMessage message);
}
