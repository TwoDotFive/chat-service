package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.application.dto.ChatroomUsersInfo;

public interface FindChatroomUsersQuery {

    ChatroomUsersInfo doService(long roomId);
}
