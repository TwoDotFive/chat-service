package xyz.fanpool.chat_service.adapter.out.message_broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.service.ChatroomListTopicMessageHandler;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatroomListTopicRedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatroomListTopicMessageHandler chatroomListTopicMessageHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String redisMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            StompChatMessage chatMessage = objectMapper.readValue(redisMessage, StompChatMessage.class);
            chatroomListTopicMessageHandler.doService(chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
