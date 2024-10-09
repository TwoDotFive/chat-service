package xyz.fanpool.chat_service.adapter.in.dto;

import xyz.fanpool.chat_service.application.dto.CreateChatroomCommand;

public record CreateChatroomRequest(String hostUserId, String fanpoolId) {

    public CreateChatroomCommand buildCommand(long guestUserId) {
        return new CreateChatroomCommand(Long.parseLong(hostUserId), guestUserId, Long.parseLong(fanpoolId));
    }
}
