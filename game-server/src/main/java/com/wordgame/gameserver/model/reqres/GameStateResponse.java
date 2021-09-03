package com.wordgame.gameserver.model.reqres;

import com.wordgame.gameserver.model.GameStatus;
import com.wordgame.gameserver.service.gameplay.Word;

import java.util.List;

public class GameStateResponse {
    private String nickname;
    private long score;
    private GameStatus status;
    private long timeLeftMillis;
    private List<Word> words;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final GameStateResponse gameStateResponse;

        public Builder() {
            gameStateResponse = new GameStateResponse();
        }

        public Builder nickname(String nickname) {
            gameStateResponse.nickname = nickname;

            return this;
        }

        public Builder score(long score) {
            gameStateResponse.score = score;

            return this;
        }

        public Builder status(GameStatus status) {
            gameStateResponse.status = status;

            return this;
        }

        public Builder timeLeftMillis(long timeLeftMillis) {
            gameStateResponse.timeLeftMillis = timeLeftMillis;

            return this;
        }

        public Builder words(List<Word> words) {
            gameStateResponse.words = words;

            return this;
        }

        public GameStateResponse build() {
            return gameStateResponse;
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public long getTimeLeftMillis() {
        return timeLeftMillis;
    }

    public void setTimeLeftMillis(long timeLeftMillis) {
        this.timeLeftMillis = timeLeftMillis;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}