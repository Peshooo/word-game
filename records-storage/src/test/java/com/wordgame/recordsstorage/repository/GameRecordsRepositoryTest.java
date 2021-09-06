package com.wordgame.recordsstorage.repository;

import com.google.common.collect.ImmutableList;
import com.wordgame.recordsstorage.configuration.FlywayUtility;
import com.wordgame.recordsstorage.configuration.GameModesConfiguration;
import com.wordgame.recordsstorage.configuration.RepositoryConfiguration;
import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.SqlParameterQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {GameModesConfiguration.class, RepositoryConfiguration.class, GameRecordsRepository.class})
@RunWith(SpringRunner.class)
public class GameRecordsRepositoryTest {
    private static final int GAME_RECORDS_PER_GAME_MODE = 10;

    @Autowired
    private List<String> gameModes;

    @Autowired
    private FlywayUtility flywayUtility;

    @Autowired
    private GameRecordsRepository gameRecordsRepository;

    @Before
    public void setUp() {
        flywayUtility.setUp();
    }

    @After
    public void tearDown() {
        flywayUtility.tearDown();
    }

    @Test
    public void testGetEmpty() {
        gameModes.forEach(this::testGetEmpty);
    }

    private void testGetEmpty(String gameMode) {
        SqlParameterQuery getQuery = SqlParameterQueryCreator.getTopXAfterQuery(gameMode, OffsetDateTime.now().minusDays(12121), 12);
        List<GameRecord> gameRecords = gameRecordsRepository.get(getQuery);

        assertTrue(gameRecords.isEmpty());
    }

    @Test
    public void testDeleteOldRecords() {
        gameModes.forEach(this::testDeleteOldRecords);
    }

    private void testDeleteOldRecords(String gameMode) {
        GameRecord oldRecord = new GameRecord("id", "nickname", 123L, OffsetDateTime.now().minusDays(2).truncatedTo(ChronoUnit.SECONDS));
        GameRecord newRecord = new GameRecord("id2", "nickname", 128L, OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        saveGameRecord(gameMode, oldRecord);
        saveGameRecord(gameMode, newRecord);

        SqlParameterQuery deleteQuery = SqlParameterQueryCreator.deleteOldRecordsQuery(gameMode, OffsetDateTime.now().minusDays(1));
        gameRecordsRepository.update(deleteQuery);

        SqlParameterQuery getQuery = SqlParameterQueryCreator.getTopXAfterQuery(gameMode, OffsetDateTime.now().minusDays(10), 12);
        List<GameRecord> gameRecords = gameRecordsRepository.get(getQuery);

        assertEquals(1, gameRecords.size());
        assertTrue(gameRecords.contains(newRecord));
    }

    @Test
    public void testSaveAndGetMultiple() {
        gameModes.forEach(this::testSaveAndGetMultiple);
    }

    private void testSaveAndGetMultiple(String gameMode) {
        List<GameRecord> gameRecords = generateGameRecords();

        saveGameRecords(gameMode, gameRecords);
        List<GameRecord> fetchedGameRecords = getGameRecords(gameMode);

        assertEquals(gameRecords.size(), fetchedGameRecords.size());
        IntStream.range(0, gameRecords.size())
                .boxed()
                .forEach(i -> {
                    assertEquals(gameRecords.get(i), fetchedGameRecords.get(i));
                });
    }

    private List<GameRecord> generateGameRecords() {
        List<GameRecord> gameRecords = new ArrayList<>();

        return IntStream
                .range(0, GAME_RECORDS_PER_GAME_MODE)
                .boxed()
                .map(this::generateGameRecord)
                .collect(Collectors.toList());
    }

    private GameRecord generateGameRecord(int i) {
        String gameId = String.format("game_%s", i);
        String nickname = String.format("nickname_%s", i);
        long score = GAME_RECORDS_PER_GAME_MODE - i;
        OffsetDateTime createdAt = OffsetDateTime.now().minusDays(i).truncatedTo(ChronoUnit.SECONDS);

        return new GameRecord(gameId, nickname, score, createdAt);
    }

    private void saveGameRecords(String gameMode, List<GameRecord> gameRecords) {
        gameRecords.forEach(gameRecord -> saveGameRecord(gameMode, gameRecord));
    }

    private void saveGameRecord(String gameMode, GameRecord gameRecord) {
        SqlParameterQuery saveQuery = SqlParameterQueryCreator.saveQuery(gameMode, gameRecord);
        gameRecordsRepository.update(saveQuery);
    }

    private List<GameRecord> getGameRecords(String gameMode) {
        SqlParameterQuery getQuery = SqlParameterQueryCreator.getTopXAfterQuery(gameMode, OffsetDateTime.now().minusDays(311), GAME_RECORDS_PER_GAME_MODE);

        return gameRecordsRepository.get(getQuery);
    }
}
