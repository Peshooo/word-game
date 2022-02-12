package com.wordgame.webui.insights;

public class Counter {
    private String key;
    private Integer count;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Counter() {
    }

    public Counter(String key, Integer count) {
        this.key = key;
        this.count = count;
    }
}
