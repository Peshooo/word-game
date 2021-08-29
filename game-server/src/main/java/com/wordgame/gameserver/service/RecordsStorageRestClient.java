package com.wordgame.gameserver.service;

import com.wordgame.gameserver.model.GameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RecordsStorageRestClient {
    @Value("${records-storage.base-url}")
    private String baseUrl;

    @Value("${records-storage.game-records-path}")
    private String gameRecordsEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public void saveRecord(String gameMode, GameRecord gameRecord) {
        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .pathSegment(gameRecordsEndpoint, gameMode)
                .build()
                .toString();

        restTemplate.postForLocation(url, gameRecord);
    }
}