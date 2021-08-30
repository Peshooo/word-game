package com.wordgame.gameserver.service;

import com.wordgame.gameserver.model.GameMode;
import com.wordgame.gameserver.model.GameRecord;
import com.wordgame.gameserver.model.GameStatus;
import com.wordgame.gameserver.model.reqres.CreateGameResponse;
import com.wordgame.gameserver.model.reqres.GameStateResponse;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.gameplay.StandardGame;
import com.wordgame.gameserver.service.gameplay.SurvivalGame;
import com.wordgame.gameserver.service.kafka.GameRecordsMessageSender;
import com.wordgame.gameserver.service.manager.GamesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class GamesService {
    @Autowired
    private GamesManager gamesManager;

    @Autowired
    private GameRecordsMessageSender gameRecordsMessageSender;

    public CreateGameResponse createGame(GameMode gameMode, String nickname) {
        Game game = GameFactory.create(gameMode, nickname);
        gamesManager.save(game);

        return new CreateGameResponse(game.getGameId());
    }

    public GameStateResponse getGameState(String gameId) {
        Game game = gamesManager.perform(gameId, this::updateGame);
        checkIfFinished(game);

        return toGameStateResponse(game);
    }

    private Game updateGame(String gameId, Game game) {
        game.updateGame();

        return game;
    }

    private void checkIfFinished(Game game) {
        if (game.getGameStatus() == GameStatus.FINISHED) {
            onGameFinished(game);
        }
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

    public void enterWord(String gameId, String word) {
        gamesManager.perform(gameId, (id, game) -> enterWord(game, word));
    }

    private Game enterWord(Game game, String word) {
        game.enterWord(word);

        return game;
    }

    public void onGameFinished(Game game) {
        GameRecord gameRecord = new GameRecord(game.getGameId(), game.getNickname(), game.getScore(), Instant.now().toEpochMilli());
        gamesManager.delete(game.getGameId());
        gameRecordsMessageSender.send(game.getGameMode().name().toLowerCase(), gameRecord);
    }
}
