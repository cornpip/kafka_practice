package com.message.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageRequestDto {
    public enum MessageType {
        ENTER, TALK, CALLOUT
    }
    private MessageType type;
    private Long channelId;
    private Long senderId;
    private String senderName;
    private String message;
    private Long callOutId;
    private LocalDateTime createdAt = LocalDateTime.now();
}