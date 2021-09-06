package com.wordgame.recordsstorage.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordgame.recordsstorage.configuration.*;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import com.wordgame.recordsstorage.service.handler.GameRecordsMessageHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {
        GameModesConfiguration.class,
        KafkaTopicsConfiguration.class,
        KafkaConsumerConfiguration.class,
        KafkaSenderConfiguration.class,
        StandardGameRecordsMessageListener.class,
        SurvivalGameRecordsMessageListener.class})
@EmbeddedKafka(
        brokerProperties = { "listeners=PLAINTEXT://${kafka.bootstrap-servers}", "port=${kafka.port}" },
        topics = { "${kafka.topics.standard-game-records.name}", "${kafka.topics.survival-game-records.name}" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
public class GameRecordsMessageListenersTest {
    private static final GameRecordMessage GAME_RECORD_MESSAGE = new GameRecordMessage("fwefew", "kereghre", 123L, Instant.now().toEpochMilli());
    @Autowired
    private List<String> gameModes;

    @MockBean
    private GameRecordsMessageHandler gameRecordsMessageHandler;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private KafkaSender kafkaSender;

    private MessageHandlerAwaitUtility messageHandlerAwaitUtility;

    @Before
    public void setUp() {
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }

        messageHandlerAwaitUtility = new MessageHandlerAwaitUtility();

        Mockito.doAnswer(invocationOnMock -> {
            String gameMode = invocationOnMock.getArgument(0);
            GameRecordMessage gameRecordMessage = invocationOnMock.getArgument(1);
            messageHandlerAwaitUtility.handle(gameMode, gameRecordMessage);

            return null;
        }).when(gameRecordsMessageHandler).handle(Mockito.any(), Mockito.any());
    }

    @Test
    public void testMessagesPassedToHandlers() {
        messageHandlerAwaitUtility.prepare(gameModes.size());
        gameModes.forEach(gameMode -> kafkaSender.send(gameMode, GAME_RECORD_MESSAGE));
        Map<String, List<GameRecordMessage>> invocationHistory = messageHandlerAwaitUtility.await();

        assertEquals(gameModes.size(), invocationHistory.size());
        gameModes.forEach(gameMode -> {
           List<GameRecordMessage> invocationHistoryForGameMode = invocationHistory.get(gameMode);
           assertEquals(1, invocationHistoryForGameMode.size());
           assertTrue(invocationHistoryForGameMode.contains(GAME_RECORD_MESSAGE));
        });
    }
}
