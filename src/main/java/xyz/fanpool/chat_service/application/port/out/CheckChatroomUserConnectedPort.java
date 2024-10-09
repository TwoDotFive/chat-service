package xyz.fanpool.chat_service.application.port.out;

public interface CheckChatroomUserConnectedPort {

    boolean checkUserConnected(long userId, long roomId);
}
