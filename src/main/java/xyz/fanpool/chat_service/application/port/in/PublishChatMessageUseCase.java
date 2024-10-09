package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.application.dto.StompChatMessage;

public interface PublishChatMessageUseCase {

    void doService(StompChatMessage stompChatMessage);
}
