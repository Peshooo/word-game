package com.wordgame.webui.service;

import com.wordgame.webui.model.CreateGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GameServerRestClient {
    @Value("${game-server.base-url}")
    private String baseUrl;

    @Value("${game-server.create-game-path}")
    private String createGamePath;

    @Autowired
    private RestTemplate restTemplate;

    public String createGame(String gameMode, String nickname) {
        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .pathSegment(createGamePath, gameMode)
                .queryParam("nickname", nickname)
                .build()
                .toString();

        return restTemplate.postForObject(url, null, CreateGameResponse.class)
                .getGameId();
    }
}
