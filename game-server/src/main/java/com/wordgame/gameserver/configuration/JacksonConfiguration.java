package com.wordgame.gameserver.configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wordgame.gameserver.service.gameplay.Word;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        DeterministicObjectMapper.CustomComparators customComparators = new DeterministicObjectMapper.CustomComparators();
        customComparators.addConverter(Word.class, Comparator.comparing(Word::getWord).thenComparing(Word::getColor));

        return DeterministicObjectMapper.create(objectMapper, customComparators);
    }
}
