package xyz.fanpool.chat_service.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessagePreview(String content, LocalDateTime time) {

    public static ChatMessagePreview getInitialPreview() {
        return new ChatMessagePreview("", LocalDateTime.now());
    }

    public static ChatMessagePreview of(ChatMessage message) {
        if(message == null) return null;
        return new ChatMessagePreview(message.getContentPreview(), message.getTime());
    }
}
