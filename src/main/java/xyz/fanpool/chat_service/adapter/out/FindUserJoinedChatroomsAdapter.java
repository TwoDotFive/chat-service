package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomJpaEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaRepository;
import xyz.fanpool.chat_service.application.dto.ChatroomUserInfo;
import xyz.fanpool.chat_service.application.port.out.FindUserJoinedChatroomsPort;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUserJoinedChatroomsAdapter implements FindUserJoinedChatroomsPort {

    private final ChatroomUserJpaRepository chatroomUserJpaRepository;

    @Override
    public List<ChatroomUserInfo> doService(long userId) {
        return chatroomUserJpaRepository.findByUserIdWithAssociatedChatroom(userId)
                .stream()
                .map(this::buildChatroomUserInfo)
                .toList();
    }

    private ChatroomUserInfo buildChatroomUserInfo(ChatroomUserJpaEntity chatroomUser) {

        ChatroomJpaEntity chatroom = chatroomUser.getChatroom();

        boolean isHost = chatroomUser.getUserId().equals(chatroom.getHostUserId());

        long partnerUserId = isHost ? chatroom.getGuestUserId() : chatroom.getHostUserId();

        return ChatroomUserInfo.builder()
                .roomId(chatroom.getId())
                .fanpoolId(chatroom.getFanpoolId())
                .partnerId(partnerUserId)
                .isHost(isHost)
                .teams(chatroom.getTeams())
                .lastMessage(chatroom.getLastMessage().toDomainEntity())
                .lastActivityTime(chatroomUser.getLastActivityTime())
                .build();
    }
}
