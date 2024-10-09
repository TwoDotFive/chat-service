package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.domain.ChatMessage;
import xyz.fanpool.chat_service.application.dto.FindChatRoomMessagesCommand;

import java.util.List;

public interface FindChatroomMessagesQuery {

    List<ChatMessage> doService(FindChatRoomMessagesCommand command);
}
