package xyz.fanpool.chat_service.application.port.out;

public interface UpdateChatroomUserConnectionStatusPort {

    void updateConnectionStatus(long userId, long roomId, boolean connected);
}
