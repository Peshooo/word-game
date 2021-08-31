package com.wordgame.recordsstorage.configuration.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        objectMapper = new ObjectMapper();
    }

    public void send(String topic, GameRecordMessage message) {
        String jsonMessage = toJsonMessage(message);
        kafkaTemplate.send(topic, jsonMessage);
    }

    private String toJsonMessage(GameRecordMessage message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
