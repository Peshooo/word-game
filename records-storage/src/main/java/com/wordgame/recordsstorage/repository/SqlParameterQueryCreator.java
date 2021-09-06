package com.wordgame.recordsstorage.repository;

import com.google.common.collect.ImmutableMap;
import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.SqlParameterQuery;

import java.time.OffsetDateTime;
import java.util.Map;

public class SqlParameterQueryCreator {
    private static final String TABLE_NAME_FORMAT = "%s_records";

    private static final String SAVE_GAME_RECORD_QUERY_FORMAT =
            "INSERT INTO %s ( game_id, nickname, score, created_at ) VALUES ( :game_id, :nickname, :score, :created_at )";

    private static final String GET_TOP_X_AFTER_QUERY_FORMAT =
            "SELECT * FROM %s WHERE created_at > :created_at_threshold ORDER BY score DESC LIMIT :limit ";

    private static final String DELETE_OLD_RECORDS_QUERY_FORMAT =
            "DELETE FROM %s WHERE created_at <= :created_at_threshold ";

    public static SqlParameterQuery saveQuery(String gameMode, GameRecord gameRecord) {
        String tableName = getTableName(gameMode);

        return saveQueryForTable(tableName, gameRecord);
    }

    private static SqlParameterQuery saveQueryForTable(String tableName, GameRecord gameRecord) {
        String query = String.format(SAVE_GAME_RECORD_QUERY_FORMAT, tableName);
        Map<String, Object> parameters = buildSaveQueryParameters(gameRecord);

        return new SqlParameterQuery(query, parameters);
    }

    private static Map<String, Object> buildSaveQueryParameters(GameRecord gameRecord) {
        return ImmutableMap.<String, Object>builder()
                .put("game_id", gameRecord.getGameId())
                .put("nickname", gameRecord.getNickname())
                .put("score", gameRecord.getScore())
                .put("created_at", gameRecord.getCreatedAt())
                .build();
    }

    public static SqlParameterQuery getTopXAfterQuery(String gameMode, OffsetDateTime createdAtThreshold, int limit) {
        String tableName = getTableName(gameMode);

        return getTopXAfterQueryForTable(tableName, createdAtThreshold, limit);
    }

    private static SqlParameterQuery getTopXAfterQueryForTable(String tableName, OffsetDateTime createdAtThreshold, int limit) {
        String query = String.format(GET_TOP_X_AFTER_QUERY_FORMAT, tableName);
        Map<String, Object> parameters = buildGetTopXAfterQueryParameters(createdAtThreshold, limit);

        return new SqlParameterQuery(query, parameters);
    }

    private static Map<String, Object> buildGetTopXAfterQueryParameters(OffsetDateTime createdAt, int limit) {
        return ImmutableMap.<String, Object>builder()
                .put("created_at_threshold", createdAt)
                .put("limit", limit)
                .build();
    }

    public static SqlParameterQuery deleteOldRecordsQuery(String gameMode, OffsetDateTime createdAtThreshold) {
        String tableName = getTableName(gameMode);

        return deleteOldRecordsQueryForTable(tableName, createdAtThreshold);
    }

    private static SqlParameterQuery deleteOldRecordsQueryForTable(String tableName, OffsetDateTime createdAtThreshold) {
        String query = String.format(DELETE_OLD_RECORDS_QUERY_FORMAT, tableName);
        Map<String, Object> parameters = buildDeleteOldRecordsQueryParameters(createdAtThreshold);

        return new SqlParameterQuery(query, parameters);
    }

    private static Map<String, Object> buildDeleteOldRecordsQueryParameters(OffsetDateTime createdAtThreshold) {
        return ImmutableMap.<String, Object>builder()
                .put("created_at_threshold", createdAtThreshold)
                .build();
    }

    private static String getTableName(String gameMode) {
        return String.format(TABLE_NAME_FORMAT, gameMode);
    }
}
