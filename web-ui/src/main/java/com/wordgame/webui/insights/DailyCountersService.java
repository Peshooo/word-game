package com.wordgame.webui.insights;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class DailyCountersService {
    private ConcurrentHashMap<DailyCounterKey, Integer> counters = new ConcurrentHashMap<>();

    public void count(String key) {
        DailyCounterKey dailyCounterKey = new DailyCounterKey(key, LocalDate.now());
        counters.putIfAbsent(dailyCounterKey, 0);
        counters.merge(dailyCounterKey, 1, Integer::sum);
    }

    public DailyCountersResponse get() {
        return new DailyCountersResponse(counters
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(entry -> entry.getKey().getDate()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry
                                .getValue()
                                .stream()
                                .map(e -> new Counter(e.getKey().getKey(), e.getValue()))
                                .collect(Collectors.toList()))));
    }
}
