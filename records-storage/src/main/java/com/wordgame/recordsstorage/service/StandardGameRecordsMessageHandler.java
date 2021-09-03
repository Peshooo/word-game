package com.wordgame.recordsstorage.service;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import org.springframework.stereotype.Service;

@Service
public class StandardGameRecordsMessageHandler extends AbstractGameRecordsMessageHandler {
    @Override
    public void handle(GameRecordMessage gameRecordMessage) {
        StandardGameRecord standardGameRecord = GameRecordMessageTranslator.standardGameRecord(gameRecordMessage);
        gameRecordsService.save(standardGameRecord);
    }
}
