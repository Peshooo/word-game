package com.wordgame.gameserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.gameserver.model.reqres.CreateGameResponse;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import com.wordgame.gameserver.service.manager.GamesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/game")
public class CreateGameController {
    @Autowired
    private GamesManager gamesManager;

    @PostMapping("/standard")
    @ResponseBody
    public CreateGameResponse createStandardGame(@RequestParam String nickname) {
        String gameId = UUID.randomUUID().toString();
        StandardGame standardGame = new StandardGame(gameId, nickname);
        gamesManager.save(standardGame);

        return new CreateGameResponse(standardGame.getGameId());
    }

    @PostMapping("/survival")
    @ResponseBody
    public CreateGameResponse createSurvivalGame(@RequestParam String nickname) {
        String gameId = UUID.randomUUID().toString();

        SurvivalGame survivalGame = new SurvivalGame(gameId, nickname);
        gamesManager.save(survivalGame);

        return new CreateGameResponse(survivalGame.getGameId());
    }
}
