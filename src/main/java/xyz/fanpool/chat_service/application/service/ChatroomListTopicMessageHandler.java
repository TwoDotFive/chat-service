package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.service.event.StompMessageSendRequestedEvent;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;

@Service
@RequiredArgsConstructor
public class ChatroomListTopicMessageHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void doService(StompChatMessage chatMessage) {
        applicationEventPublisher.publishEvent(StompMessageSendRequestedEvent.builder()
                .destination("/chatroom/sub/" + chatMessage.getReceiverId())
                .message(chatMessage)
        );
    }
}
