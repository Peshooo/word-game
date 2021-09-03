package com.wordgame.recordsstorage.listener;

import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.service.StandardGameRecordsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class StandardGameRecordsMessageListener implements GameRecordsMessageListener {
    @Autowired
    private StandardGameRecordsMessageHandler standardGameRecordsMessageHandler;

    @Override
    @KafkaListener(
            topics = "${kafka.topics.standard-game-records.name}",
            groupId = "${kafka.topics.standard-game-records.consumer-group}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listen(@Payload GameRecordMessage gameRecordMessage) {
        standardGameRecordsMessageHandler.handle(gameRecordMessage);
    }
}
