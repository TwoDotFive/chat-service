package xyz.fanpool.chat_service.application.port.out;

import java.time.LocalDateTime;

public interface UpdateChatroomLastMessagePort {

    void doService(long roomId, String content, LocalDateTime time);
}
