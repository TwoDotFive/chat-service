package xyz.fanpool.chat_service.application.port.out;

public interface CheckMemberOfChatroomPort {

    boolean doService(long userId, long chatroomId);
}
