package com.wordgame.recordsstorage.service;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameRecordsMessageHandler {
    @Autowired
    private GameRecordsRepository gameRecordsRepository;

    public void handle(GameRecordMessage gameRecordMessage) {
        SurvivalGameRecord survivalGameRecord = GameRecordMessageTranslator.survivalGameRecord(gameRecordMessage);
        gameRecordsRepository.save(survivalGameRecord);
    }
}
