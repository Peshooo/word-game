package com.wordgame.recordsstorage.configuration.util;

import com.wordgame.recordsstorage.model.GameRecordMessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MessageHandlerAwaitUtility {
    private static final long MAX_AWAIT_TIME_MILLISECONDS = 30_000;

    private final List<GameRecordMessage> invocationHistory;

    private CountDownLatch countDownLatch;

    public MessageHandlerAwaitUtility() {
        invocationHistory = new CopyOnWriteArrayList<>();
    }

    public void prepare(int count) {
        countDownLatch = new CountDownLatch(count);
    }

    public void handle(GameRecordMessage message) {
        invocationHistory.add(message);
        countDownLatch.countDown();;
    }

    public void await() {
        try {
            countDownLatch.await(MAX_AWAIT_TIME_MILLISECONDS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameRecordMessage> getInvocationHistory() {
        return invocationHistory;
    }
}
