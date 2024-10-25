package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.PublishChatMessageUseCase;
import xyz.fanpool.chat_service.application.port.out.PublishChatMessagePort;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;


@Service
@RequiredArgsConstructor
public class PublishChatMessageService implements PublishChatMessageUseCase {

    private final PublishChatMessagePort publishChatMessagePort;

    @Override
    @Transactional
    public void doService(StompChatMessage stompChatMessage) {
        publishChatMessagePort.doService(stompChatMessage);
    }
}
