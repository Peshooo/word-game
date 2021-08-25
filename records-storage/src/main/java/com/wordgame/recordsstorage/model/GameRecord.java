package com.wordgame.recordsstorage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GameRecord {
    @Id
    private String gameId;

    private String nickname;
    private Long score;
    private OffsetDateTime createdAt;

    public GameRecord() {
    }

    @PersistenceConstructor
    public GameRecord(String gameId, String nickname, Long score, OffsetDateTime createdAt) {
        this.gameId = gameId;
        this.nickname = nickname;
        this.score = score;
        this.createdAt = createdAt;
    }

    public String getGameId() {
        return gameId;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getScore() {
        return score;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
