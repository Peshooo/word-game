package com.wordgame.recordsstorage.service.fetcher;

import com.wordgame.recordsstorage.configuration.Constants;
import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordsResponse;
import com.wordgame.recordsstorage.model.SqlParameterQuery;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import com.wordgame.recordsstorage.repository.SqlParameterQueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameRecordsFetcher {
    @Autowired
    private GameRecordsRepository gameRecordsRepository;

    @Autowired
    private List<String> gameModes;

    public GameRecordsResponse fetchGameRecords() {
        Map<String, List<GameRecord>> gameRecordsByGameModes = fetchGameRecordsForAllGameModes();

        return new GameRecordsResponse(gameRecordsByGameModes);
    }

    private Map<String, List<GameRecord>> fetchGameRecordsForAllGameModes() {
        return gameModes.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        this::fetchGameRecordsForGameMode));
    }

    private List<GameRecord> fetchGameRecordsForGameMode(String gameMode) {
        OffsetDateTime createdAtThreshold = OffsetDateTime.now().minusDays(Constants.OLD_RECORDS_DELTA_DAYS);
        int limit = Constants.GET_RECORDS_LIMIT;

        return fetchGameRecords(gameMode, createdAtThreshold, limit);
    }

    private List<GameRecord> fetchGameRecords(String gameMode, OffsetDateTime createdAtThreshold, int limit) {
        SqlParameterQuery getRecordsQuery = SqlParameterQueryCreator.getTopXAfterQuery(gameMode, createdAtThreshold, limit);

        return gameRecordsRepository.get(getRecordsQuery);
    }
}
