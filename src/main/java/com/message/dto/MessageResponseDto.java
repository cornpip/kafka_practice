package com.message.dto;

import lombok.Getter;

import java.time.LocalDateTime;

//임시
@Getter
public class MessageResponseDto {
    private Long id; //db 들렸다 나와서 해당 id가 필요
    private Long userId;
    private String userEmail;
    private String userName;
    private Long channelId;
    private Long callOutContentId;
    private LocalDateTime createdAt;
    private Long notReadCount;
    private String text;
    private MessageRequestDto.MessageType type;

    public MessageResponseDto(MessageRequestDto requestDto){
        this.userId = requestDto.getSenderId();
        this.userName = requestDto.getSenderName();
        this.channelId = requestDto.getChannelId();
        this.callOutContentId = requestDto.getCallOutId();
        this.createdAt = requestDto.getCreatedAt();
        this.text = requestDto.getMessage();
        this.type = requestDto.getType();
    }
}
