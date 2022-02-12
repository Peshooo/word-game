package com.wordgame.webui.service;

import com.wordgame.webui.game.model.GameMode;
import com.wordgame.webui.game.service.GamesService;
import com.wordgame.webui.model.CreateGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GameServerRestClient {
    @Autowired
    private GamesService gamesService;

    public String createGame(String gameMode, String nickname) {
        CreateGameResponse createGameResponse = gamesService.createGame(GameMode.valueOf(gameMode.toUpperCase()), nickname);

        return createGameResponse.getGameId();
    }
}
