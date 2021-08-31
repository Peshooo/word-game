package com.wordgame.recordsstorage.configuration;

import com.wordgame.recordsstorage.configuration.util.MessageHandlerAwaitUtility;
import com.wordgame.recordsstorage.service.StandardGameRecordsMessageHandler;
import com.wordgame.recordsstorage.service.SurvivalGameRecordsMessageHandler;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({KafkaTopicsConfiguration.class, KafkaProducerConfiguration.class, KafkaConsumerConfiguration.class})
public class KafkaTestConfiguration {
    @Bean
    public MessageHandlerAwaitUtility messageHandlerAwaitUtility() {
        return new MessageHandlerAwaitUtility();
    }

    @Bean
    public StandardGameRecordsMessageHandler standardGameRecordsMessageHandlerMock(MessageHandlerAwaitUtility messageHandlerAwaitUtility) {
        StandardGameRecordsMessageHandler standardGameRecordsMessageHandlerMock = Mockito.mock(StandardGameRecordsMessageHandler.class);
        Mockito.doAnswer(invocation -> {
            messageHandlerAwaitUtility.handle(invocation.getArgument(0));

            return null;
        }).when(standardGameRecordsMessageHandlerMock).handle(Mockito.any());

        return standardGameRecordsMessageHandlerMock;
    }

    @Bean
    public SurvivalGameRecordsMessageHandler survivalGameRecordsMessageHandlerMock(MessageHandlerAwaitUtility messageHandlerAwaitUtility) {
        SurvivalGameRecordsMessageHandler survivalGameRecordsMessageHandlerMock = Mockito.mock(SurvivalGameRecordsMessageHandler.class);
        Mockito.doAnswer(invocation -> {
            messageHandlerAwaitUtility.handle(invocation.getArgument(0));

            return null;
        }).when(survivalGameRecordsMessageHandlerMock).handle(Mockito.any());

        return survivalGameRecordsMessageHandlerMock;
    }
}
