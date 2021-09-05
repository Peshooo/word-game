package com.wordgame.recordsstorage.configuration;

import com.wordgame.recordsstorage.model.GameMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class GameModesConfiguration {
    @Bean
    public List<String> gameModes() {
        return Arrays.stream(GameMode.values())
                .map(GameMode::name)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
