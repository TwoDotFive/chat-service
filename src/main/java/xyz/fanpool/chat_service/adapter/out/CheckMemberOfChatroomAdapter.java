package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaRepository;
import xyz.fanpool.chat_service.application.port.out.CheckMemberOfChatroomPort;

@Component
@RequiredArgsConstructor
public class CheckMemberOfChatroomAdapter implements CheckMemberOfChatroomPort {

    private final ChatroomUserJpaRepository chatroomUserJpaRepository;

    @Override
    public boolean doService(long userId, long chatroomId) {
        return chatroomUserJpaRepository.existsByUserIdAndChatroomId(userId, chatroomId);
    }
}
