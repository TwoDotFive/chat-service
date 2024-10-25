package xyz.fanpool.chat_service.application.dto;

import lombok.Builder;
import xyz.fanpool.chat_service.domain.ChatMessagePreview;

import java.time.LocalDateTime;

@Builder
public record ChatroomPreview(
        String roomId,
        String fanpoolId,
        boolean isHost,
        UserProfile partner,
        LocalDateTime lastActivityTime,
        String teams,
        ChatMessagePreview lastMessage
) {

    public static ChatroomPreview build(ChatroomUserInfo chatroomUserInfo, UserProfileDto partnerProfileDto) {
        return ChatroomPreview.builder()
                .roomId(String.valueOf(chatroomUserInfo.roomId()))
                .fanpoolId(String.valueOf(chatroomUserInfo.fanpoolId()))
                .isHost(chatroomUserInfo.isHost())
                .partner(new UserProfile(partnerProfileDto.id(), partnerProfileDto.nickname(), partnerProfileDto.image()))
                .lastActivityTime(chatroomUserInfo.lastActivityTime())
                .teams(chatroomUserInfo.teams())
                .lastMessage(chatroomUserInfo.lastMessage())
                .build();
    }
}
