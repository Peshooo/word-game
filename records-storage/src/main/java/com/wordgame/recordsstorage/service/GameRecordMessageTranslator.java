package com.wordgame.recordsstorage.service;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class GameRecordMessageTranslator {
    public static StandardGameRecord standardGameRecord(GameRecordMessage gameRecordMessage) {
        return new StandardGameRecord(
                gameRecordMessage.getGameId(),
                gameRecordMessage.getNickname(),
                gameRecordMessage.getScore(),
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(gameRecordMessage.getCreatedAt()), ZoneOffset.UTC));
    }

    public static SurvivalGameRecord survivalGameRecord(GameRecordMessage gameRecordMessage) {
        return new SurvivalGameRecord(
                gameRecordMessage.getGameId(),
                gameRecordMessage.getNickname(),
                gameRecordMessage.getScore(),
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(gameRecordMessage.getCreatedAt()), ZoneOffset.UTC));
    }
}
