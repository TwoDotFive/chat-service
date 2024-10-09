package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.out.UpdateChatroomUserLastActivityTimePort;
import xyz.fanpool.chat_service.application.port.in.UpdateChatroomUserLastActivityTimeUseCase;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateChatroomUserLastActivityTimeService implements UpdateChatroomUserLastActivityTimeUseCase {

    private final UpdateChatroomUserLastActivityTimePort updateChatroomUserLastActivityTimePort;

    @Override
    @Transactional
    public void doService(long userId, long roomId) {
        updateChatroomUserLastActivityTimePort.doService(userId, roomId, LocalDateTime.now());
    }
}
