package com.wordgame.recordsstorage.configuration;

import com.wordgame.recordsstorage.model.GameRecordMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MessageHandlerAwaitUtility {
    private static final int MAX_TIME_AWAIT_MILLIS = 30_000;

    private ConcurrentMap<String, List<GameRecordMessage>> invocationHistory;
    private CountDownLatch countDownLatch;

    public void prepare(int count) {
        invocationHistory = new ConcurrentHashMap<>();
        countDownLatch = new CountDownLatch(count);
    }

    public void handle(String gameMode, GameRecordMessage gameRecordMessage) {
        invocationHistory.putIfAbsent(gameMode, new ArrayList<>());
        invocationHistory.compute(gameMode, (key, value) -> {
            value.add(gameRecordMessage);

            return value;
        });
        countDownLatch.countDown();
    }

    public Map<String, List<GameRecordMessage>> await() {
        try {
            countDownLatch.await(MAX_TIME_AWAIT_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return invocationHistory;
    }
}
