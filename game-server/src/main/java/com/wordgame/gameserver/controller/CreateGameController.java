package com.wordgame.gameserver.controller;

import com.wordgame.gameserver.model.reqres.CreateGameResponse;
import com.wordgame.gameserver.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/game")
public class CreateGameController {
    @Autowired
    private GamesService gamesService;

    @PostMapping("/standard")
    @ResponseBody
    public CreateGameResponse createStandardGame(@RequestParam String nickname) {
        return gamesService.createStandardGame(nickname);
    }

    @PostMapping("/survival")
    @ResponseBody
    public CreateGameResponse createSurvivalGame(@RequestParam String nickname) {
        return gamesService.createSurvivalGame(nickname);
    }
}
