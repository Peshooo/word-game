package com.wordgame.gameserver.controller;

import com.wordgame.gameserver.model.reqres.CreateGameResponse;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import com.wordgame.gameserver.service.manager.GamesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class CreateGameController {
    @Autowired
    private GamesManager gamesManager;

    @PostMapping("/standard")
    public CreateGameResponse createStandardGame(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");
        String gameId = UUID.randomUUID().toString();

        StandardGame standardGame = new StandardGame(nickname, gameId);
        gamesManager.save(standardGame);

        return new CreateGameResponse(standardGame.getGameId());
    }

    @PostMapping("/survival")
    public CreateGameResponse createSurvivalGame(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");
        String gameId = UUID.randomUUID().toString();

        SurvivalGame survivalGame = new SurvivalGame(nickname, gameId);
        gamesManager.save(survivalGame);

        return new CreateGameResponse(survivalGame.getGameId());
    }
}
