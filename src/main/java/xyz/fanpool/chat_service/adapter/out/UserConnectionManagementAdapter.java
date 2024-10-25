package xyz.fanpool.chat_service.adapter.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xyz.fanpool.chat_service.application.port.out.DeleteUserConnectedServerAddressPort;
import xyz.fanpool.chat_service.application.port.out.FindUserConnectedServerAddressPort;
import xyz.fanpool.chat_service.application.port.out.SaveUserConnectedServerAddressPort;

@Repository
public class UserConnectionManagementAdapter
		implements SaveUserConnectedServerAddressPort, FindUserConnectedServerAddressPort,
		DeleteUserConnectedServerAddressPort {

	private final HashOperations<String, String, String> opsHashSocketConnection;

	private static final String KEY = "USER_CONNECTION";

	@Autowired
	public UserConnectionManagementAdapter(RedisTemplate<String, Object> redisTemplate) {
		this.opsHashSocketConnection = redisTemplate.opsForHash();
	}

	@Override
	public void saveUserConnectedServerAddress(long userId, String serverIpAddress) {
		opsHashSocketConnection.put(KEY, String.valueOf(userId), serverIpAddress);
	}

	@Override
	public String findUserConnectedServerIpAddress(long userId) {
		return opsHashSocketConnection.get(KEY, String.valueOf(userId));
	}

	@Override
	public void delete(long userId) {
		opsHashSocketConnection.delete(KEY, String.valueOf(userId));
	}
}
