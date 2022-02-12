package com.wordgame.webui.game.model;

public class WordMatches {
    private long matchesCount;
    private String word;

    public WordMatches(long matchesCount, String word) {
        this.matchesCount = matchesCount;
        this.word = word;
    }

    public long getMatchesCount() {
        return matchesCount;
    }

    public String getWord() {
        return word;
    }
}
