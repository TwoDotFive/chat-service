package xyz.fanpool.chat_service.adapter.out;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.SaveDeviceTokenPort;
import xyz.fanpool.chat_service.application.port.out.SendPushNotificationPort;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PushNotificationFirebaseAdapter implements SaveDeviceTokenPort, SendPushNotificationPort {

	private final StringRedisTemplate redisTemplate;

	private static final String DEVICE_TOKEN = "deviceToken";
	private static final int EXPIRED_AFTER_DAYS = 60;

	@Override
	public void save(long userId, String token) {
		redisTemplate.opsForValue().set(getKey(userId), token, EXPIRED_AFTER_DAYS, TimeUnit.DAYS);
	}

	@Override
	public void sendNotification(long userId, String title, String body) {

		if (!redisTemplate.hasKey(getKey(userId))) {
			return;
		}

		String token = redisTemplate.opsForValue().get(getKey(userId));

		WebpushNotification webpushNotification = WebpushNotification.builder().setTitle(title).setBody(body).build();

		Message message = Message.builder()
				.setWebpushConfig(WebpushConfig.builder().setNotification(webpushNotification).build()).setToken(token).build();

		FirebaseMessaging.getInstance().sendAsync(message);
	}

	private String getKey(long userId) {
		return String.format("%s:%d", DEVICE_TOKEN, userId);
	}
}
