package xyz.fanpool.chat_service.application.port.out;

public interface SaveDeviceTokenPort {

    void save(long userId, String token);
}
