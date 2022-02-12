package com.wordgame.webui.insights;

import java.time.LocalDate;
import java.util.Objects;

public class DailyCounterKey {
    private String key;
    private LocalDate date;

    public DailyCounterKey(String key, LocalDate date) {
        this.key = key;
        this.date = date;
    }

    public DailyCounterKey() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyCounterKey that = (DailyCounterKey) o;
        return Objects.equals(key, that.key) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, date);
    }
}
