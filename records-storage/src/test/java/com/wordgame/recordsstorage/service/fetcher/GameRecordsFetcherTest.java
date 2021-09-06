package com.wordgame.recordsstorage.service.fetcher;

import com.google.common.collect.ImmutableList;
import com.wordgame.recordsstorage.configuration.GameModesConfiguration;
import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordsResponse;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.junit.Before;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {GameModesConfiguration.class, GameRecordsFetcher.class})
@RunWith(SpringRunner.class)
public class GameRecordsFetcherTest {
    private static final List<GameRecord> GAME_RECORDS = ImmutableList.of(
            new GameRecord("ddeq", "oehiew", 123L, OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)),
            new GameRecord("u3h", "odjf", 99L, OffsetDateTime.now().minusMinutes(7).truncatedTo(ChronoUnit.SECONDS)));

    @MockBean
    private GameRecordsRepository gameRecordsRepository;

    @Autowired
    private GameRecordsFetcher gameRecordsFetcher;

    @Autowired
    private List<String> gameModes;

    @Before
    public void setUp() {
        Mockito.when(gameRecordsRepository.get(Mockito.any())).thenReturn(GAME_RECORDS);
    }

    @Test
    public void testFetchGameRecords() {
        GameRecordsResponse gameRecordsResponse = gameRecordsFetcher.fetchGameRecords();
        Map<String, List<GameRecord>> gameRecordsByGameMode = gameRecordsResponse.getGameRecordsByGameMode();

        assertEquals(gameModes.size(), gameRecordsByGameMode.size());
        gameModes.forEach(gameMode -> {
            List<GameRecord> gameRecords = gameRecordsByGameMode.get(gameMode);
            assertEquals(GAME_RECORDS.size(), gameRecords.size());
            assertTrue(GAME_RECORDS.containsAll(gameRecords));
        });
    }
}
