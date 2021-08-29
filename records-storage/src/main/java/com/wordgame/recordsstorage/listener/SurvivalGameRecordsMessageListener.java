package com.wordgame.recordsstorage.listener;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import com.wordgame.recordsstorage.service.SurvivalGameRecordsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameRecordsMessageListener {
    @Autowired
    private SurvivalGameRecordsMessageHandler survivalGameRecordsMessageHandler;

    @KafkaListener(
            topics = "${kafka.topics.survival-game-records.name}",
            groupId = "${kafka.topics.survival-game-records.consumer-group}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listen(@Payload GameRecordMessage gameRecordMessage) {
        survivalGameRecordsMessageHandler.handle(gameRecordMessage);
    }
}