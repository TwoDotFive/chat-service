package xyz.fanpool.chat_service.application.port.in;

public interface SaveDeviceTokenUseCase {

    void doService(long userId, String token);
}
