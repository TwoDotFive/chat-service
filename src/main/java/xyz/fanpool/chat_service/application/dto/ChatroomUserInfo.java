package xyz.fanpool.chat_service.application.dto;

import lombok.Builder;
import xyz.fanpool.chat_service.domain.ChatMessagePreview;

import java.time.LocalDateTime;

@Builder
public record ChatroomUserInfo(
        long roomId,
        long fanpoolId,
        long partnerId,
        boolean isHost,
        String teams,
        ChatMessagePreview lastMessage,
        LocalDateTime lastActivityTime
) {
}
