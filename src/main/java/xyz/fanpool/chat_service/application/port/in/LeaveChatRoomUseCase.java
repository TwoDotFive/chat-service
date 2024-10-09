package xyz.fanpool.chat_service.application.port.in;

public interface LeaveChatRoomUseCase {

    void doService(long userId, long roomId);
}
