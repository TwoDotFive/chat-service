package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.application.dto.StompChatMessage;

public interface PublishChatMessagePort {

    void doService(StompChatMessage message);
}
