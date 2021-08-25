package com.wordgame.recordsstorage.controller;

import com.wordgame.recordsstorage.model.GameRecord;
import com.wordgame.recordsstorage.model.StandardGameRecord;
import com.wordgame.recordsstorage.model.SurvivalGameRecord;
import com.wordgame.recordsstorage.repository.GameRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/game-records")
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

    @GetMapping("/standard")
    public List<? extends GameRecord> getStandard() {
        return gameRecordsRepository.getTopFiveLastDayStandard();
    }

    @GetMapping("/survival")
    public List<? extends GameRecord> getSurvival() {
        return gameRecordsRepository.getTopFiveLastDaySurvival();
    }
}
