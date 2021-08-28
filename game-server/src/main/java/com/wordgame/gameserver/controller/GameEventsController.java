package com.wordgame.gameserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.gameserver.model.GameStatus;
import com.wordgame.gameserver.model.reqres.GameStateResponse;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.manager.GamesManager;
import com.wordgame.gameserver.service.manager.RedisGamesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

//TODO: Create service
@Controller
@CrossOrigin("${websocket.client}")
public class GameEventsController {
    @Autowired
    private GamesManager gamesManager;

    @MessageMapping("/{gameId}/{word}")
    public void enterWord(@DestinationVariable String gameId, @DestinationVariable String word) {
        gamesManager.perform(gameId, (id, game) -> enterWord(game, word));
    }

    private Game enterWord(Game game, String word) {
        game.enterWord(word);

        return game;
    }

    @MessageMapping("/{gameId}")
    @SendTo("/game-state/{gameId}")
    public GameStateResponse getGameState(@DestinationVariable String gameId) {
        Game game = gamesManager.perform(gameId, this::updateGame);
        if (game.getGameStatus() == GameStatus.FINISHED) {
            gamesManager.delete(gameId); //TODO: Record finished game
        }

        return toGameStateResponse(game);
    }

    private Game updateGame(String gameId, Game game) {
        game.updateGame();

        return game;
    }

    //TODO: Create translator classes
    private GameStateResponse toGameStateResponse(Game game) {
        GameStateResponse gameStateResponse = new GameStateResponse();
        gameStateResponse.setNickname(game.getNickname());
        gameStateResponse.setStatus(game.getGameStatus());
        gameStateResponse.setScore(game.getScore());
        gameStateResponse.setTimeLeftMillis(game.getTimeLeftMillis());
        gameStateResponse.setWords(game.getWords());

        return gameStateResponse;
    }
}
