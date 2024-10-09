package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.UpdateChatroomUserConnectionStatusUseCase;
import xyz.fanpool.chat_service.application.port.out.UpdateChatroomUserConnectionStatusPort;

@Service
@RequiredArgsConstructor
public class UpdateChatroomUserConnectionStatusService implements UpdateChatroomUserConnectionStatusUseCase {

    private final UpdateChatroomUserConnectionStatusPort updateChatroomUserConnectionStatusPort;

    @Override
    @Transactional
    public void doService(long userId, long roomId, boolean connected) {
        updateChatroomUserConnectionStatusPort.updateConnectionStatus(userId, roomId, connected);
    }
}
