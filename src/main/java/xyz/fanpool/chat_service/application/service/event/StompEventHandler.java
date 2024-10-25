package xyz.fanpool.chat_service.application.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompEventHandler {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    protected void stompMessageSendRequestedEventHandler(StompMessageSendRequestedEvent event) {
        simpMessageSendingOperations.convertAndSend(event.destination(), event.message());
    }
}
