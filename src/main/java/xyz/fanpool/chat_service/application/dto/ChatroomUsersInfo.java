package xyz.fanpool.chat_service.application.dto;

import lombok.Builder;

@Builder
public record ChatroomUsersInfo(long hostUserId, long guestUserId) {
}
