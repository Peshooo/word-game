package com.wordgame.recordsstorage;

import com.wordgame.recordsstorage.model.GameRecordsResponse;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRecordsService {
    private static final String EVERY_DAY_AT_MIDNIGHT_CRON = "0 0 0 * * ?";

    @Autowired
    private GameRecordsRepository gameRecordsRepository;

    public void saveStandardRecord(StandardGameRecord standardGameRecord) {

    }

    public void saveSurvivalRecord(SurvivalGameRecord survivalGameRecord) {

    }

    public GameRecordsResponse getTopFiveGameRecordsLastDay() {
        List<StandardGameRecord> standardGameRecords = gameRecordsRepository.getTopFiveLastDayStandard();
        List<SurvivalGameRecord> survivalGameRecords = gameRecordsRepository.getTopFiveLastDaySurvival();

        return new GameRecordsResponse(standardGameRecords, survivalGameRecords);
    }

    @Scheduled(cron = EVERY_DAY_AT_MIDNIGHT_CRON)
    public void deleteOldRecords() {
        gameRecordsRepository.deleteOldStandardRecords();
        gameRecordsRepository.deleteOldSurvivalRecords();
        ;
    }
}
