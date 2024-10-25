package xyz.fanpool.chat_service.application.port.out;

public interface UnsubscribeChatroomTopicPort {

    void unsubscribe(String channel);
}
