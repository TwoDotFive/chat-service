package xyz.fanpool.chat_service.application.dto;

import xyz.fanpool.chat_service.domain.ChatMessageType;

public record SaveChatMessageCommand(
        long roomId,
        long senderId,
        ChatMessageType type,
        String content
) {
}
