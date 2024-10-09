package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.application.dto.CreateChatroomCommand;

public interface CreateChatroomUseCase {

    long doService(CreateChatroomCommand command);
}
