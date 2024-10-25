package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.PublishChatMessagePort;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;

@Component
@RequiredArgsConstructor
public class PublishChatMessageRedisAdapter implements PublishChatMessagePort {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void doService(StompChatMessage message) {
        redisTemplate.convertAndSend("room_" + message.getRoomId(), message);
        redisTemplate.convertAndSend("user_" + message.getReceiverId(), message);
    }
}
