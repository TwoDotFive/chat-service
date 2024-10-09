package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.application.service.event.StompMessageSendRequestedEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomTopicMessageHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void doService(StompChatMessage chatMessage) {
        long roomId = chatMessage.getRoomId();

        applicationEventPublisher.publishEvent(StompMessageSendRequestedEvent.builder()
                .destination("/chat/sub/" + roomId)
                .message(chatMessage)
                .build()
        );
    }

}
