package com.wordgame.recordsstorage.service.persister;

import com.wordgame.recordsstorage.configuration.GameModesConfiguration;
import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.List;

@SpringBootTest(classes = {GameModesConfiguration.class, GameRecordsPersister.class})
@RunWith(SpringRunner.class)
public class GameRecordsPersisterTest {
    private static final GameRecord GAME_RECORD = new GameRecord("safjqe", "jerghjer", 123333L, OffsetDateTime.now());
    @Autowired
    private List<String> gameModes;

    @MockBean
    private GameRecordsRepository gameRecordsRepository;

    @Autowired
    private GameRecordsPersister gameRecordsPersister;

    @Test
    public void testPersisterCallsRepositorySave() {
        int totalCalls = 0;

        for (String gameMode : gameModes) {
            gameRecordsPersister.save(gameMode, GAME_RECORD);
            ++totalCalls;

            Mockito.verify(gameRecordsRepository, Mockito.times(totalCalls)).update(Mockito.any());
        }
    }
}
