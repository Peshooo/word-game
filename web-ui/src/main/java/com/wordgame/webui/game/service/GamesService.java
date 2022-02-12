package com.wordgame.webui.game.service;

import com.wordgame.webui.game.model.*;
import com.wordgame.webui.game.service.manager.GamesManager;
import com.wordgame.webui.game.util.GameStateResponseTranslator;
import com.wordgame.webui.model.CreateGameResponse;
import com.wordgame.webui.model.GameRecord;
import com.wordgame.webui.service.RecordsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GamesService {
    @Autowired
    private GamesManager gamesManager;

    @Autowired
    private RecordsStorageService recordsStorageService;

    @Autowired
    private DeleteScheduler deleteScheduler;

    private ConcurrentHashMap<String, Long> pollHistory = new ConcurrentHashMap<>();

    public CreateGameResponse createGame(GameMode gameMode, String nickname) {
        Game game = GameFactory.create(gameMode, nickname);
        gamesManager.save(game);

        return new CreateGameResponse(game.getGameId());
    }

    @Scheduled(fixedRate = 10000)
    public void pingHungGames() {
        Long threshold = System.currentTimeMillis() - 10000;
        List<String> toDelete = pollHistory
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() < threshold)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        toDelete.forEach(pollHistory::remove);
        toDelete.forEach(this::getGameState);
    }

    public GameStateResponse getGameState(String gameId) {
        pollHistory.put(gameId, System.currentTimeMillis());

        Game game = gamesManager.perform(gameId, this::updateGame);

        if (game == null) {
            return null;
        }

        checkIfFinished(game);

        return GameStateResponseTranslator.translate(game);
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

    public void enterWord(String gameId, String word) {
        gamesManager.perform(gameId, (id, game) -> enterWord(game, word));
    }

    private Game enterWord(Game game, String word) {
        game.enterWord(word);

        return game;
    }

    public void onGameFinished(Game game) {
        GameRecord gameRecord = new GameRecord(game.getGameId(), game.getNickname(), game.getScore(), Instant.now().toEpochMilli());
        deleteScheduler.schedule(game.getGameId());
        recordsStorageService.save(game.getGameMode().name().toLowerCase(), gameRecord);
    }
}