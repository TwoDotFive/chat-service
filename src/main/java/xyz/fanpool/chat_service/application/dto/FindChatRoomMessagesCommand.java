package xyz.fanpool.chat_service.application.dto;

public record FindChatRoomMessagesCommand(Long userId, Long roomId, Long lastMessageId, int pageSize) {
}
