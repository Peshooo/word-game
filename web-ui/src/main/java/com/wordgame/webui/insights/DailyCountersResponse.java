package com.wordgame.webui.insights;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DailyCountersResponse {
    private Map<LocalDate, List<Counter>> dailyCounters;

    public DailyCountersResponse(Map<LocalDate, List<Counter>> dailyCounters) {
        this.dailyCounters = dailyCounters;
    }

    public Map<LocalDate, List<Counter>> getDailyCounters() {
        return dailyCounters;
    }

    public void setDailyCounters(Map<LocalDate, List<Counter>> dailyCounters) {
        this.dailyCounters = dailyCounters;
    }

    public DailyCountersResponse() {
    }
}
