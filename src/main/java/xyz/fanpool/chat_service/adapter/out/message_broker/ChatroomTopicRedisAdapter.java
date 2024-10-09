package xyz.fanpool.chat_service.adapter.out.message_broker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.SubscribeChatroomTopicPort;

@Component
@RequiredArgsConstructor
public class ChatroomTopicRedisAdapter implements SubscribeChatroomTopicPort {

    private final RedisMessageListenerContainer redisMessageListener;
    private final ChatroomTopicRedisSubscriber chatroomTopicRedisSubscriber;

    @Override
    public void subscribe(String channel) {
        redisMessageListener.addMessageListener(chatroomTopicRedisSubscriber, new ChannelTopic(channel));
    }
}
