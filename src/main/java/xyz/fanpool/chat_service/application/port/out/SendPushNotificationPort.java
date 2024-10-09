package xyz.fanpool.chat_service.application.port.out;

public interface SendPushNotificationPort {

    void sendNotification(long userId, String title, String body);
}
