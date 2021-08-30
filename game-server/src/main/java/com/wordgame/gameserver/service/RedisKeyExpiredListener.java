package com.wordgame.gameserver.service;

import com.wordgame.gameserver.model.GameRecord;
import com.wordgame.gameserver.service.gameplay.Game;
import com.wordgame.gameserver.service.kafka.GameRecordsMessageSender;
import com.wordgame.gameserver.service.manager.GamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    private GameRecordsMessageSender gameRecordsMessageSender;

    @Autowired
    private GamesManager gamesManager;

    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());

        if (messageBody.contains("expiration.")) {
            int index = messageBody.indexOf("expiration.");
            int gameIdStartIndex = index + 11;

            String gameId = messageBody.substring(gameIdStartIndex);
            Game game = gamesManager.get(gameId);

            if (game == null) {
                return;
            }

            GameRecord gameRecord = new GameRecord(game.getGameId(), game.getNickname(), game.getScore(), Instant.now().toEpochMilli());
            gamesManager.delete(gameId);
            gameRecordsMessageSender.send(game.getGameMode().name().toLowerCase(), gameRecord);
        }
    }
}