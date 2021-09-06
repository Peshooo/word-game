package com.wordgame.recordsstorage.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSender {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String topicFormat;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSender(String topicFormat, KafkaTemplate<String, String> kafkaTemplate) {
        this.topicFormat = topicFormat;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String gameMode, GameRecordMessage gameRecordMessage) {
        String topic = String.format(topicFormat, gameMode);
        String jsonMessage = toJsonMessage(gameRecordMessage);
        kafkaTemplate.send(topic, gameRecordMessage.getGameId(), jsonMessage);
    }

    private String toJsonMessage(GameRecordMessage gameRecordMessage) {
        try {
            return objectMapper.writeValueAsString(gameRecordMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
