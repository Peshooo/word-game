package com.wordgame.recordsstorage.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "standard_records")
public class StandardGameRecord extends GameRecord {
    public StandardGameRecord() {
    }

    public StandardGameRecord(String gameId, String nickname, Long score, OffsetDateTime createdAt) {
        super(gameId, nickname, score, createdAt);
    }
}
