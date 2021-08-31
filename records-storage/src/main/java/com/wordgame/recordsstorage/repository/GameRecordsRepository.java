package com.wordgame.recordsstorage.repository;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRecordsRepository extends CrudRepository<GameRecord, String> {
    String GET_TOP_FIVE_LAST_DAY_STANDARD = "SELECT * " +
            "FROM standard_records " +
            "WHERE created_at >= DATE_SUB(NOW(),INTERVAL 1 DAY) " +
            "ORDER BY score DESC " +
            "LIMIT 5 ";
    String GET_TOP_FIVE_LAST_DAY_SURVIVAL =
            "SELECT * " +
                    "FROM survival_records " +
                    "WHERE created_at >= DATE_SUB(NOW(),INTERVAL 1 DAY) " +
                    "ORDER BY score DESC " +
                    "LIMIT 5 ";
    String DELETE_OLD_STANDARD_RECORDS = "" +
            "DELETE FROM standard_records " + "" +
            "WHERE created_at < DATE_SUB(NOW(),INTERVAL 1 DAY) ";
    String DELETE_OLD_SURVIVAL_RECORDS = "" +
            "DELETE FROM survival_records " + "" +
            "WHERE created_at < DATE_SUB(NOW(),INTERVAL 1 DAY) ";

    @Query(value = GET_TOP_FIVE_LAST_DAY_STANDARD, nativeQuery = true)
    List<StandardGameRecord> getTopFiveLastDayStandard();

    @Query(value = GET_TOP_FIVE_LAST_DAY_SURVIVAL, nativeQuery = true)
    List<SurvivalGameRecord> getTopFiveLastDaySurvival();

    @Query(value = DELETE_OLD_STANDARD_RECORDS, nativeQuery = true)
    void deleteOldStandardRecords();

    @Query(value = DELETE_OLD_SURVIVAL_RECORDS, nativeQuery = true)
    void deleteOldSurvivalRecords();
}
