package com.wordgame.recordsstorage.configuration;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaTopicsConfiguration {
    @Bean
    public KafkaAdmin kafkaAdmin(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
        Map<String, Object> configuration = ImmutableMap.<String, Object>builder()
                .put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
                .build();

        return new KafkaAdmin(configuration);
    }

    @Bean
    public NewTopic survivalGameRecordsTopic(
            @Value("${kafka.topics.survival-game-records.name}") String name,
            @Value("${kafka.topics.survival-game-records.partitions}") int partitions,
            @Value("${kafka.topics.survival-game-records.replication-factor}") int replicationFactor) {

        return TopicBuilder
                .name(name)
                .partitions(partitions)
                .replicas(replicationFactor)
                .build();
    }

    @Bean
    public NewTopic standardGameRecordsTopic(
            @Value("${kafka.topics.standard-game-records.name}") String name,
            @Value("${kafka.topics.standard-game-records.partitions}") int partitions,
            @Value("${kafka.topics.standard-game-records.replication-factor}") int replicationFactor) {

        return TopicBuilder
                .name(name)
                .partitions(partitions)
                .replicas(replicationFactor)
                .build();
    }
}
