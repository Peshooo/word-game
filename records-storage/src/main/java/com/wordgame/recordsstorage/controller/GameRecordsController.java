package com.wordgame.recordsstorage.controller;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.GameRecordsResponse;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-records")
public class GameRecordsController {
    @Autowired
    private GameRecordsRepository gameRecordsRepository;

    @PostMapping("/standard")
    public void saveStandard(@RequestBody StandardGameRecord gameRecord) {
        gameRecordsRepository.save(gameRecord);
    }

    @PostMapping("/survival")
    public void saveSurvival(@RequestBody SurvivalGameRecord gameRecord) {
        gameRecordsRepository.save(gameRecord);
    }

    @GetMapping
    public GameRecordsResponse getGameRecords() {
        List<StandardGameRecord> standardGameRecords = gameRecordsRepository.getTopFiveLastDayStandard();
        List<SurvivalGameRecord> survivalGameRecords = gameRecordsRepository.getTopFiveLastDaySurvival();

        return new GameRecordsResponse(standardGameRecords, survivalGameRecords);
    }
}
