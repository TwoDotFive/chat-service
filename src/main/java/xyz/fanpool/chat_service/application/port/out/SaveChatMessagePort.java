package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.domain.ChatMessage;

public interface SaveChatMessagePort {

    void doService(ChatMessage chatMessage);
}
