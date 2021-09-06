package com.wordgame.recordsstorage.handler;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.service.handler.GameRecordMessageTranslator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = {GameRecordMessageTranslator.class})
public class GameRecordMessageTranslatorTest {
    @Test
    public void testTranslate() {
        GameRecord expectedGameRecord = new GameRecord("asfofjqw", "38uwiowe", 127L, OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        GameRecordMessage gameRecordMessage = new GameRecordMessage(expectedGameRecord.getGameId(), expectedGameRecord.getNickname(), expectedGameRecord.getScore(), expectedGameRecord.getCreatedAt().toInstant().toEpochMilli());

        GameRecord translatedGameRecord = GameRecordMessageTranslator.translate(gameRecordMessage);
        assertEquals(expectedGameRecord, translatedGameRecord);
    }
}
