package com.wordgame.webui.service;

import com.wordgame.webui.model.GameRecord;
import com.wordgame.webui.model.GameRecordsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class RecordsStorageRestClient {
    @Value("${records-storage.base-url}")
    private String baseUrl;

    @Value("${records-storage.game-records-endpoint}")
    private String gameRecordsEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    public GameRecordsResponse getGameRecords() {
        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .pathSegment(gameRecordsEndpoint)
                .build()
                .toString();

        return restTemplate.getForObject(url, GameRecordsResponse.class);
    }
}
