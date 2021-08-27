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