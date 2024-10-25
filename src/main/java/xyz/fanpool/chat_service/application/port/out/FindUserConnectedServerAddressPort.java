package xyz.fanpool.chat_service.application.port.out;

public interface FindUserConnectedServerAddressPort {

    String findUserConnectedServerIpAddress(long userId);
}
