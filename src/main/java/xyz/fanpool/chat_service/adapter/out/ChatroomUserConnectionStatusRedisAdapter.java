package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xyz.fanpool.chat_service.application.port.out.UpdateChatroomUserConnectionStatusPort;
import xyz.fanpool.chat_service.application.port.out.CheckChatroomUserConnectedPort;

@Repository
@RequiredArgsConstructor
public class ChatroomUserConnectionStatusRedisAdapter implements
        UpdateChatroomUserConnectionStatusPort,
        CheckChatroomUserConnectedPort
{

    private static final String KEY = "SOCKET_CONNECTION";
    private final HashOperations<String, String, Boolean> opsHashSocketConnection;

    @Autowired
    public ChatroomUserConnectionStatusRedisAdapter(RedisTemplate<String, Object> redisTemplate) {
        this.opsHashSocketConnection = redisTemplate.opsForHash();
    }

    @Override
    public void updateConnectionStatus(long userId, long roomId, boolean connected) {
        opsHashSocketConnection.put(KEY, getHashKey(userId, roomId), true);
    }

    @Override
    public boolean checkUserConnected(long userId, long roomId) {
        return opsHashSocketConnection.hasKey(KEY, getHashKey(userId, roomId));
    }

    private String getHashKey(long userId, long roomId) {
        return userId + "_" + roomId;
    }
}
