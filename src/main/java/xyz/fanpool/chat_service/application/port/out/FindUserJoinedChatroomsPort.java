package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.application.dto.ChatroomUserInfo;

import java.util.List;

public interface FindUserJoinedChatroomsPort {

    List<ChatroomUserInfo> doService(long userId);
}
