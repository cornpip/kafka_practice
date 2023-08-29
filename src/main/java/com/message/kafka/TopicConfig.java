package com.message.kafka;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@RequiredArgsConstructor
public class TopicConfig {
    @NonNull
    private KafkaAdmin kafkaAdmin;
    public final static String MSG_TOPIC = "MSG_TOPIC";

    public NewTopic messageTopic() {
        return TopicBuilder.name(MSG_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @PostConstruct
    public void init() {
        kafkaAdmin.createOrModifyTopics(messageTopic());
    }
}
