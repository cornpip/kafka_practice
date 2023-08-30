package com.message.controller;

import com.message.dto.MessageRequestDto;
import com.message.dto.MessageResponseDto;
import com.message.dto.MessageTestDto;
import com.message.kafka.TopicConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static com.message.kafka.TopicConfig.MSG_TOPIC;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    @NonNull
    private final SimpMessageSendingOperations messagingTemplate;
    @NonNull
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Async
    @MessageMapping("/chat/test")
    public void message(MessageTestDto msg) {
        log.info(msg.toString());
        CompletableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(MSG_TOPIC, 1, "send success");
        future.whenComplete((result, ex) -> {
            System.out.println("future : " + result.getProducerRecord().value());
        });
//                messagingTemplate.convertAndSend("/sub/chat/room/" + msg.getChannelId(), msg);
    }


    @Async
    @MessageMapping("/chat/message")
    public void message(MessageRequestDto requestDto) {
        if (MessageRequestDto.MessageType.ENTER.equals(requestDto.getType())) {
            requestDto.setMessage(requestDto.getSenderName() + "님이 입장하셨습니다.");
        }
        MessageResponseDto responseDto = new MessageResponseDto(requestDto);
        messagingTemplate.convertAndSend("/sub/chat/room/" + requestDto.getChannelId(), responseDto);
    }

    @KafkaListener(id = "foo", topics = MSG_TOPIC, clientIdPrefix = "myClientId")
    public void listen(String data) {
        System.out.println(data);
    }
}
