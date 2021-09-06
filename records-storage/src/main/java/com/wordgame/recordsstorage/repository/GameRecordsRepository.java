package com.wordgame.recordsstorage.repository;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.SqlParameterQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRecordsRepository {
    private static final BeanPropertyRowMapper<GameRecord> ROW_MAPPER = new BeanPropertyRowMapper<>(GameRecord.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void update(SqlParameterQuery sqlParameterQuery) {
        jdbcTemplate.update(sqlParameterQuery.getQuery(), sqlParameterQuery.getParameters());
    }

    public List<GameRecord> get(SqlParameterQuery sqlParameterQuery) {
        return jdbcTemplate.query(sqlParameterQuery.getQuery(), sqlParameterQuery.getParameters(), ROW_MAPPER);
    }
}
