package com.wordgame.recordsstorage.listener;

import com.wordgame.recordsstorage.model.GameRecordMessage;

public interface GameRecordsMessageListener {
    void listen(GameRecordMessage message);
}
