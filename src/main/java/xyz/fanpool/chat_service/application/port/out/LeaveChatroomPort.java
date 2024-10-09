package xyz.fanpool.chat_service.application.port.out;

public interface LeaveChatroomPort {

    void doService(long userId, long roomId);
}
