package com.wordgame.recordsstorage.service;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardGameRecordsMessageHandler {
    @Autowired
    private GameRecordsRepository gameRecordsRepository;

    public void handle(GameRecordMessage gameRecordMessage) {
        StandardGameRecord standardGameRecord = GameRecordMessageTranslator.standardGameRecord(gameRecordMessage);
        gameRecordsRepository.save(standardGameRecord);
    }
}
