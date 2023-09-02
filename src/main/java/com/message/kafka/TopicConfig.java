package com.message.kafka;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static com.message.kafka.KafKaConstant.BROKER_SERVER;
import static com.message.kafka.KafKaConstant.MSG_TOPIC;

@Configuration
@RequiredArgsConstructor
public class TopicConfig {
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_SERVER);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(MSG_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }
}
