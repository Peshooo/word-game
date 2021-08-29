package com.wordgame.gameserver.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.gameserver.model.GameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameRecordsMessageSender {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic-format}")
    private String topicFormat;

    public void send(String gameMode, GameRecord gameRecord) {
        String topic = getTopic(gameMode);
        String key = gameRecord.getGameId();
        String value = toJsonMessage(gameRecord);

        kafkaTemplate.send(topic, key, value);
    }

    private String getTopic(String gameMode) {
        return String.format(topicFormat, gameMode);
    }

    private String toJsonMessage(GameRecord gameRecord) {
        try {
            return objectMapper.writeValueAsString(gameRecord);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
