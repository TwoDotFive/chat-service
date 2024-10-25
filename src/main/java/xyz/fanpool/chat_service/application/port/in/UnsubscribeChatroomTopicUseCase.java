package xyz.fanpool.chat_service.application.port.in;

public interface UnsubscribeChatroomTopicUseCase {

    void doService(long userId, long roomId);
}
