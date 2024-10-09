package xyz.fanpool.chat_service.application.dto;

public record CreateChatroomCommand(
        long hostUserId,
        long guestUserId,
        long fanpoolId
) {
}
