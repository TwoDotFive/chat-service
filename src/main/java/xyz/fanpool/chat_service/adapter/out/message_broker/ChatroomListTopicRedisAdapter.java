package xyz.fanpool.chat_service.adapter.out.message_broker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import xyz.fanpool.chat_service.application.port.out.SubscribeChatroomListTopicPort;
import xyz.fanpool.chat_service.application.port.out.UnsubscribeChatroomListTopicPort;

@Repository
@RequiredArgsConstructor
public class ChatroomListTopicRedisAdapter implements
        SubscribeChatroomListTopicPort,
        UnsubscribeChatroomListTopicPort {

    private final RedisMessageListenerContainer redisMessageListener;
    private final ChatroomListTopicRedisSubscriber chatroomListTopicRedisSubscriber;

    @Override
    public void subscribe(String channel) {
        redisMessageListener.addMessageListener(chatroomListTopicRedisSubscriber, new ChannelTopic(channel));
    }

    @Override
    public void unsubscribe(String channel) {
        redisMessageListener.removeMessageListener(chatroomListTopicRedisSubscriber, new ChannelTopic(channel));
    }
}
