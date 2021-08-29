package com.wordgame.recordsstorage.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "survival_records")
public class SurvivalGameRecord extends GameRecord {
    public SurvivalGameRecord() {
    }

    public SurvivalGameRecord(String gameId, String nickname, Long score, OffsetDateTime createdAt) {
        super(gameId, nickname, score, createdAt);
    }
}
