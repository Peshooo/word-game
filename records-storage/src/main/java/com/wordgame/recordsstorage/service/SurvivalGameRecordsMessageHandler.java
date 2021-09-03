package com.wordgame.recordsstorage.service;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameRecordsMessageHandler extends AbstractGameRecordsMessageHandler {
    @Override
    public void handle(GameRecordMessage gameRecordMessage) {
        SurvivalGameRecord survivalGameRecord = GameRecordMessageTranslator.survivalGameRecord(gameRecordMessage);
        gameRecordsService.save(survivalGameRecord);
    }
}
