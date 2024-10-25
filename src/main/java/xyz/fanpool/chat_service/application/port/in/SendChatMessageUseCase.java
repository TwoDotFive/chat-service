package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.application.dto.StompChatMessage;

public interface SendChatMessageUseCase {

    void doService(StompChatMessage stompChatMessage, long receiverId);
}
