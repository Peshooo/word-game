package com.wordgame.gameserver.controller;

import com.wordgame.gameserver.model.reqres.CreateGameResponse;
import com.wordgame.gameserver.service.gameplay.AbstractGame;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import com.wordgame.gameserver.service.manager.StandardGamesManager;
import com.wordgame.gameserver.service.manager.SurvivalGamesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private StandardGamesManager standardGamesManager;
    @Autowired
    private SurvivalGamesManager survivalGamesManager;

    @PostMapping("/standard")
    public CreateGameResponse createStandardGame(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");
        String gameId = UUID.randomUUID().toString();

        StandardGame standardGame = new StandardGame(nickname, gameId);
        standardGamesManager.save(standardGame);

        return new CreateGameResponse(standardGame.getGameId());
    }

    @PostMapping("/survival")
    public CreateGameResponse createSurvivalGame(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");
        String gameId = UUID.randomUUID().toString();

        SurvivalGame survivalGame = new SurvivalGame(nickname, gameId);
        survivalGamesManager.save(survivalGame);

        return new CreateGameResponse(survivalGame.getGameId());
    }
}
