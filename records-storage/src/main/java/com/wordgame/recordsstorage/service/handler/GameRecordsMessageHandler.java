package com.wordgame.recordsstorage.service.handler;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.service.persister.GameRecordsPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameRecordsMessageHandler {
    @Autowired
    private GameRecordsPersister gameRecordsPersister;

    public void handle(String gameMode, GameRecordMessage message) {
        GameRecord gameRecord = GameRecordMessageTranslator.translate(message);
        gameRecordsPersister.save(gameMode, gameRecord);
    }
}
