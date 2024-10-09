package xyz.fanpool.chat_service.application.port.in;

public interface SubscribeChatroomTopicUseCase {

    void doService(long userId, long roomId);
}
