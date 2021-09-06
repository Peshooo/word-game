package com.wordgame.recordsstorage.service.handler;

import com.wordgame.recordsstorage.configuration.GameModesConfiguration;
import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.service.persister.GameRecordsPersister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest(classes = {GameModesConfiguration.class, GameRecordsMessageHandler.class})
@RunWith(SpringRunner.class)
public class GameRecordsMessageHandlerTest {
    private static final GameRecord GAME_RECORD = new GameRecord("vjrr", "jhrhre", 12143L, OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    private static final GameRecordMessage GAME_RECORD_MESSAGE = new GameRecordMessage(GAME_RECORD.getGameId(), GAME_RECORD.getNickname(), GAME_RECORD.getScore(), GAME_RECORD.getCreatedAt().toInstant().toEpochMilli());

    @Autowired
    private List<String> gameModes;

    @MockBean
    private GameRecordsPersister gameRecordsPersister;

    @Autowired
    private GameRecordsMessageHandler gameRecordsMessageHandler;

    @Test
    public void testGameRecordsMessageHandlerCallsPersisterSave() {
        gameModes.forEach(gameMode -> gameRecordsMessageHandler.handle(gameMode, GAME_RECORD_MESSAGE));
        gameModes.forEach(gameMode -> Mockito.verify(gameRecordsPersister, Mockito.times(1)).save(gameMode, GAME_RECORD));
    }
}
