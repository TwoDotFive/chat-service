package xyz.fanpool.chat_service.adapter.in.dto;

import xyz.fanpool.chat_service.domain.ChatMessage;
import xyz.fanpool.chat_service.domain.ChatMessageType;

import java.time.LocalDateTime;

public record ChatMessageView(
        String id,
        String senderId,
        ChatMessageType type,
        String content,
        LocalDateTime time
) {

    public static ChatMessageView of(ChatMessage chatMessage) {
        return new ChatMessageView(
                String.valueOf(chatMessage.getId()),
                String.valueOf(chatMessage.getUserId()),
                chatMessage.getType(),
                chatMessage.getContent(),
                chatMessage.getTime()
        );
    }
}
