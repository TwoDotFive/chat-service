package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.dto.ChatroomUsersInfo;
import xyz.fanpool.chat_service.application.port.in.FindChatroomUsersQuery;
import xyz.fanpool.chat_service.application.port.out.FindChatroomPort;
import xyz.fanpool.chat_service.domain.Chatroom;

@Service
@RequiredArgsConstructor
public class FindChatroomUsersService implements FindChatroomUsersQuery {
    
    private final FindChatroomPort findChatroomPort;
    
    @Override
    public ChatroomUsersInfo doService(long roomId) {
        Chatroom chatroom = findChatroomPort.doService(roomId)
                .orElseThrow(IllegalArgumentException::new);

        return ChatroomUsersInfo.builder()
                .hostUserId(chatroom.getHostUserId())
                .guestUserId(chatroom.getGuestUserId())
                .build();
    }
}
