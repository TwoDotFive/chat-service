package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaRepository;
import xyz.fanpool.chat_service.application.port.out.LeaveChatroomPort;

@Component
@RequiredArgsConstructor
public class LeaveChatroomAdapter implements LeaveChatroomPort {

    private final ChatroomUserJpaRepository chatroomUserJpaRepository;

    @Override
    public void doService(long userId, long roomId) {
        chatroomUserJpaRepository.deleteByUserIdAndChatroomId(userId, roomId);
    }
}
