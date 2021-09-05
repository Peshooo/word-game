package com.wordgame.recordsstorage.service.persister;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.SqlParameterQuery;
import com.wordgame.recordsstorage.repository.GameRecordsJdbcRepository;
import com.wordgame.recordsstorage.repository.SqlParameterQueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameRecordsPersister {
    @Autowired
    private GameRecordsJdbcRepository gameRecordsJdbcRepository;

    public void save(String gameMode, GameRecord gameRecord) {
        SqlParameterQuery saveRecordQuery = SqlParameterQueryCreator.saveQuery(gameMode, gameRecord);

        gameRecordsJdbcRepository.update(saveRecordQuery);
    }
}
