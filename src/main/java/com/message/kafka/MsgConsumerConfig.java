package com.message.kafka;

import com.message.dto.MessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class MsgConsumerConfig {
    public final String GROUP_ID = "ConsumerGroup1";

    @Bean
    public KafkaMessageListenerContainer<Integer, String> sendKafkaListenerContainerFactory() {
        ContainerProperties containerProps = new ContainerProperties(TopicConfig.MSG_TOPIC);
        containerProps.setMessageListener(new MyMessageListener());
        KafkaMessageListenerContainer<Integer, String> container = new KafkaMessageListenerContainer<>(sendConsumerFactory(), containerProps);
        return container;
    }

    public ConsumerFactory<Integer, String> sendConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(sendConsumerConfigurations(), new IntegerDeserializer(), new StringDeserializer());
    }

    public Map<String, Object> sendConsumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return configurations;
    }

    static class MyMessageListener implements MessageListener<Integer, String> {
        @Override
        public void onMessage(ConsumerRecord<Integer, String> data) {
            log.info("MyMessageListener : "+data.value());
        }
    }
}
