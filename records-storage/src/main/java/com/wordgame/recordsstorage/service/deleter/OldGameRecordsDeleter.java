package com.wordgame.recordsstorage.service.deleter;

import com.wordgame.recordsstorage.configuration.Constants;
import com.wordgame.recordsstorage.model.SqlParameterQuery;
import com.wordgame.recordsstorage.repository.GameRecordsJdbcRepository;
import com.wordgame.recordsstorage.repository.SqlParameterQueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OldGameRecordsDeleter {
    private static final String EVERY_DAY_AT_MIDNIGHT_CRON = "0 0 0 * * ?";

    @Autowired
    private GameRecordsJdbcRepository gameRecordsJdbcRepository;

    @Autowired
    private List<String> gameModes;

    @Scheduled(cron = EVERY_DAY_AT_MIDNIGHT_CRON)
    public void deleteOldRecords() {
        gameModes.forEach(this::deleteOldRecords);
    }

    private void deleteOldRecords(String gameMode) {
        OffsetDateTime threshold = OffsetDateTime.now().minusDays(Constants.OLD_RECORDS_DELTA_DAYS);
        SqlParameterQuery deleteOldRecordsQuery = SqlParameterQueryCreator.deleteOldRecordsQuery(gameMode, threshold);

        gameRecordsJdbcRepository.update(deleteOldRecordsQuery);
    }
}
