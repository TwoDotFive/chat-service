package xyz.fanpool.chat_service.application.port.out;

public interface SaveUserConnectedServerAddressPort {

    void saveUserConnectedServerAddress(long userId, String serverIpAddress);
}
