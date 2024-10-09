package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.application.dto.SaveChatMessageCommand;

public interface SaveChatMessageUseCase {

    void doService(SaveChatMessageCommand command);
}
