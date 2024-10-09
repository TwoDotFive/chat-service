package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.PublishChatMessagePort;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublishChatMessageRedisAdapter implements PublishChatMessagePort {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void doService(StompChatMessage message) {
        log.info("Published : {}", message.getContent());
        redisTemplate.convertAndSend("room_" + message.getRoomId(), message);
    }
}
