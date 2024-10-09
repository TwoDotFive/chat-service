package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaRepository;
import xyz.fanpool.chat_service.application.port.out.UpdateChatroomUserLastActivityTimePort;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UpdateChatroomUserLastActivityTimeAdapter implements UpdateChatroomUserLastActivityTimePort {

    private final ChatroomUserJpaRepository chatroomUserJpaRepository;

    @Override
    public void doService(long userId, long roomId, LocalDateTime time) {
        chatroomUserJpaRepository.updateLastActivityTime(userId, roomId, time);
    }
}
