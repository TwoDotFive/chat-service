package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.LeaveChatRoomUseCase;
import xyz.fanpool.chat_service.application.port.out.CheckMemberOfChatroomPort;
import xyz.fanpool.chat_service.application.port.out.LeaveChatroomPort;
import xyz.fanpool.chat_service.common.exception.CustomException;

@Service
@RequiredArgsConstructor
public class LeaveChatRoomService implements LeaveChatRoomUseCase {

    private final LeaveChatroomPort leaveChatroomPort;
    private final CheckMemberOfChatroomPort checkMemberOfChatroomPort;

    @Override
    @Transactional
    public void doService(long userId, long roomId) {

        if (!checkMemberOfChatroomPort.doService(userId, roomId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Invalid Request");
        }

        leaveChatroomPort.doService(userId, roomId);
    }
}
