package xyz.fanpool.chat_service.application.service.event;

import lombok.Builder;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;

@Builder
public record StompMessageSendRequestedEvent(String destination, StompChatMessage message) {
}
