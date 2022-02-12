package com.wordgame.webui.service;

import com.google.common.collect.ImmutableMap;
import com.wordgame.webui.model.GameRecord;
import com.wordgame.webui.model.GameRecordsResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Service
public class RecordsStorageService {
    private ConcurrentSkipListSet<GameRecord> standardRecords = new ConcurrentSkipListSet<>(Comparator.comparing(GameRecord::getScore));
    private ConcurrentSkipListSet<GameRecord> survivalRecords = new ConcurrentSkipListSet<>(Comparator.comparing(GameRecord::getScore));

    public void save(String gameMode, GameRecord gameRecord) {
        switch (gameMode) {
            case "standard":
                standardRecords.add(gameRecord);
                break;
            case "survival":
                survivalRecords.add(gameRecord);
                break;
            default:
                throw new RuntimeException("Unrecognized gamed mode " + gameMode);
        }
    }

    public GameRecordsResponse getGameRecords() {
        return new GameRecordsResponse(
                ImmutableMap.of(
                        "standard", standardRecords.descendingSet().stream().filter(this::isNotOld).limit(5).collect(Collectors.toList()),
                        "survival", survivalRecords.descendingSet().stream().filter(this::isNotOld).limit(5).collect(Collectors.toList())));
    }

    @Scheduled(fixedRate = 60 * 60000)
    public void trim() {
        trim(standardRecords);
        trim(survivalRecords);
    }

    private void trim(ConcurrentSkipListSet<GameRecord> gameRecords) {
        gameRecords.removeIf(this::isOld);
    }

    private boolean isOld(GameRecord gameRecord) {
        long threshold = Instant.now().minus(Duration.ofHours(24)).toEpochMilli();

        return gameRecord.getCreatedAt() < threshold;
    }

    private boolean isNotOld(GameRecord gameRecord) {
        return !isOld(gameRecord);
    }
}
