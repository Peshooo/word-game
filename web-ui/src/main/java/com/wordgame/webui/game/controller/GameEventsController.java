package com.wordgame.webui.game.controller;

import com.wordgame.webui.game.model.GameStateResponse;
import com.wordgame.webui.game.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("${websocket.client}")
public class GameEventsController {
    @Autowired
    private GamesService gamesService;

    @MessageMapping("/{gameId}/{word}")
    public void enterWord(@DestinationVariable String gameId, @DestinationVariable String word) {
        gamesService.enterWord(gameId, word);
    }

    @MessageMapping("/{gameId}")
    @SendTo("/game-state/{gameId}")
    public GameStateResponse getGameState(@DestinationVariable String gameId) {
        return gamesService.getGameState(gameId);
    }
}
