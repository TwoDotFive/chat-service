package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.domain.Chatroom;

import java.util.Optional;

public interface FindChatroomPort {

    Optional<Chatroom> doService(long roomId);
}
