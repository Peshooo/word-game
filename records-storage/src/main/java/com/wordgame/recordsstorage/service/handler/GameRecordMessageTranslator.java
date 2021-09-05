package com.wordgame.recordsstorage.service.handler;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordMessage;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class GameRecordMessageTranslator {
    public static GameRecord translate(GameRecordMessage gameRecordMessage) {
        return new GameRecord(
                gameRecordMessage.getGameId(),
                gameRecordMessage.getNickname(),
                gameRecordMessage.getScore(),
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(gameRecordMessage.getCreatedAt()), ZoneOffset.UTC));
    }
}
