package xyz.fanpool.chat_service.application.port.in;

public interface UpdateChatroomUserLastActivityTimeUseCase {

    void doService(long userId, long roomId);
}
