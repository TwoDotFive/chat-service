package xyz.fanpool.chat_service.application.port.in;

public interface UpdateChatroomUserConnectionStatusUseCase {

    void doService(long userId, long roomId, boolean connected);
}
