package com.wordgame.recordsstorage.listener;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.service.handler.GameRecordsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameRecordsMessageListener implements GameRecordsMessageListener {
    @Autowired
    private GameRecordsMessageHandler gameRecordsMessageHandler;

    @Override
    @KafkaListener(
            topics = "${kafka.topics.survival-game-records.name}",
            groupId = "${kafka.topics.survival-game-records.consumer-group}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listen(@Payload GameRecordMessage gameRecordMessage) {
        gameRecordsMessageHandler.handle("survival", gameRecordMessage);
    }
}
