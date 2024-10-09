package xyz.fanpool.chat_service.application.port.out;

import java.time.LocalDateTime;

public interface UpdateChatroomUserLastActivityTimePort {

    void doService(long userId, long roomId, LocalDateTime time);
}
