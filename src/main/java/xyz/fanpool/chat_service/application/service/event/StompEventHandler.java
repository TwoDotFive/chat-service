package xyz.fanpool.chat_service.application.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompEventHandler {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    protected void stompMessageSendRequestedEventHandler(StompMessageSendRequestedEvent event) {
        log.info("SENT : {}", event.message().getContent());
        simpMessageSendingOperations.convertAndSend(event.destination(), event.message());
    }
}
