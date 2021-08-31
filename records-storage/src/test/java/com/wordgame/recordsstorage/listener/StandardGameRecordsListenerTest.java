package com.wordgame.recordsstorage.listener;

import com.wordgame.recordsstorage.configuration.KafkaTestConfiguration;
import com.wordgame.recordsstorage.configuration.util.KafkaSender;
import com.wordgame.recordsstorage.configuration.util.MessageHandlerAwaitUtility;
import com.wordgame.recordsstorage.model.GameRecordMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {KafkaTestConfiguration.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@EmbeddedKafka(
        brokerProperties = {"listeners=PLAINTEXT://${kafka.bootstrap-servers}", "port=${kafka.port}"},
        topics = {"${kafka.topics.standard-game-records.name}", "${kafka.topics.survival-game-records.name}"})
public class StandardGameRecordsListenerTest {
    private static final GameRecordMessage GAME_RECORD_MESSAGE = new GameRecordMessage("game_id", "nickname", 123L, 838928932L);
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private MessageHandlerAwaitUtility messageHandlerAwaitUtility;

    @Autowired
    private KafkaSender kafkaSender;

    @Value("${kafka.topics.standard-game-records-topic.name}")
    private String standardRecordsTopic;

    @Value("${kafka.topics.survival-game-records-topic.name}")
    private String survivalRecordsTopic;

    @Before
    public void setUp() {
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }

    @Test
    public void testStandardGameRecordMessageReceived() {
        testGameRecordMessageReceived(standardRecordsTopic);
    }

    @Test
    public void testSurvivalGameRecordMessageReceived() {
        testGameRecordMessageReceived(survivalRecordsTopic);
    }

    private void testGameRecordMessageReceived(String topic) {
        messageHandlerAwaitUtility.prepare(1);
        kafkaSender.send(topic, GAME_RECORD_MESSAGE);
        messageHandlerAwaitUtility.await();
        List<GameRecordMessage> invocationHistory = messageHandlerAwaitUtility.getInvocationHistory();
        assertEquals(1, invocationHistory.size());
        assertTrue(invocationHistory.contains(GAME_RECORD_MESSAGE));
    }
}
