package com.wordgame.recordsstorage.repository;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.SqlParameterQuery;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {SqlParameterQueryCreator.class})
public class SqlParameterQueryCreatorTest {
    @Test
    public void testBuildSaveGameRecordQuery() {
        String gameMode = "whatever_game_mode";
        GameRecord gameRecord = new GameRecord("game_id_ppqqdsp2", "pesho420", 179L, OffsetDateTime.now());
        SqlParameterQuery saveQuery = SqlParameterQueryCreator.saveQuery(gameMode, gameRecord);

        String expectedQuery = String.format("INSERT INTO %s_records ( game_id, nickname, score, created_at ) VALUES ( :game_id, :nickname, :score, :created_at )", gameMode);
        assertEquals(expectedQuery, saveQuery.getQuery());

        Map<String, Object> parameters = saveQuery.getParameters();
        assertEquals(4, parameters.size());
        assertEquals(parameters.get("game_id"), gameRecord.getGameId());
        assertEquals(parameters.get("nickname"), gameRecord.getNickname());
        assertEquals(parameters.get("score"), gameRecord.getScore());
        assertTrue(gameRecord.getCreatedAt().isEqual((OffsetDateTime) parameters.get("created_at")));
    }

    @Test
    public void testBuildGetGameRecordsQuery() {
        String gameMode = "some_mode";
        OffsetDateTime createdAtThreshold = OffsetDateTime.now().minusHours(19L);
        int limit = 42;

        SqlParameterQuery getQuery = SqlParameterQueryCreator.getTopXAfterQuery(gameMode, createdAtThreshold, limit);

        String expectedQuery = String.format("SELECT * FROM %s_records WHERE created_at > :created_at_threshold ORDER BY score DESC LIMIT :limit ", gameMode);
        assertEquals(expectedQuery, getQuery.getQuery());

        Map<String, Object> parameters = getQuery.getParameters();
        assertEquals(2, parameters.size());
        assertTrue(createdAtThreshold.isEqual((OffsetDateTime) parameters.get("created_at_threshold")));
        assertEquals(limit, parameters.get("limit"));
    }

    @Test
    public void testBuildDeleteOldGameRecordsQuery() {
        String gameMode = "minecraft";
        OffsetDateTime createdAtThreshold = OffsetDateTime.now().minusHours(179L);

        SqlParameterQuery deleteQuery = SqlParameterQueryCreator.deleteOldRecordsQuery(gameMode, createdAtThreshold);

        String expectedQuery = String.format("DELETE FROM %s_records WHERE created_at <= :created_at_threshold ", gameMode);
        assertEquals(expectedQuery, deleteQuery.getQuery());

        Map<String, Object> parameters = deleteQuery.getParameters();
        assertEquals(1, parameters.size());
        assertTrue(createdAtThreshold.isEqual((OffsetDateTime) parameters.get("created_at_threshold")));
    }
}
