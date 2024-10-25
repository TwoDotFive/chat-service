package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.application.dto.StompChatMessage;

public interface TransferChatMessagePort {

    boolean doService(StompChatMessage message, String targetServerIpAddress);
}
