package xyz.fanpool.chat_service.adapter.out.message_broker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.SubscribeChatroomTopicPort;
import xyz.fanpool.chat_service.application.port.out.UnsubscribeChatroomTopicPort;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatroomTopicRedisAdapter implements
        SubscribeChatroomTopicPort,
        UnsubscribeChatroomTopicPort
{

    private final RedisMessageListenerContainer redisMessageListener;
    private final ChatroomTopicRedisSubscriber chatroomTopicRedisSubscriber;
    private final Map<String, Integer> topicSubscriberCounts = new ConcurrentHashMap<>();

    @Override
    public void subscribe(String channel) {
        topicSubscriberCounts.compute(channel, (k, v) -> v == null ? 1 : v + 1);
        if (topicSubscriberCounts.get(channel) == 1) {
            redisMessageListener.addMessageListener(chatroomTopicRedisSubscriber, new ChannelTopic(channel));
        }
    }

    @Override
    public void unsubscribe(String channel) {
        topicSubscriberCounts.computeIfPresent(channel, (k, v) -> v - 1);
        if (topicSubscriberCounts.getOrDefault(channel, 0) == 0) {
            redisMessageListener.removeMessageListener(chatroomTopicRedisSubscriber, new ChannelTopic(channel));
        }
    }
}
