package com.wordgame.recordsstorage.controller;

import com.wordgame.recordsstorage.GameRecordsService;
import com.wordgame.recordsstorage.model.GameRecordsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-records")
public class GameRecordsController {
    @Autowired
    private GameRecordsService gameRecordsService;

    @GetMapping
    public GameRecordsResponse getGameRecords() {
        return gameRecordsService.getTopFiveGameRecordsLastDay();
    }
}
