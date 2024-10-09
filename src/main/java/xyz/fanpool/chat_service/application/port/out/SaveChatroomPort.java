package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.domain.Chatroom;
import xyz.fanpool.chat_service.domain.ChatroomUser;

public interface SaveChatroomPort {

    void doService(Chatroom chatroom, ChatroomUser hostChatroom, ChatroomUser guestChatroom);
}
