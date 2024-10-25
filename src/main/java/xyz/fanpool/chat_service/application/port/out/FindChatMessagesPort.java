package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.domain.ChatMessage;

import java.util.List;

public interface FindChatMessagesPort {

    List<ChatMessage> doService(long roomId, long lastChatMessageId, int pageSize);
}
