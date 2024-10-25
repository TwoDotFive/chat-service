package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.port.in.UnsubscribeChatroomTopicUseCase;
import xyz.fanpool.chat_service.application.port.out.UnsubscribeChatroomTopicPort;

@Service
@RequiredArgsConstructor
public class UnsubscribeChatroomTopicService implements UnsubscribeChatroomTopicUseCase {

    private static final String CHATROOM_TOPIC_PREFIX = "room_";

    private final UnsubscribeChatroomTopicPort unsubscribeChatroomTopicPort;

    @Override
    public void doService(long userId, long roomId) {
        unsubscribeChatroomTopicPort.unsubscribe(CHATROOM_TOPIC_PREFIX + roomId);
    }
}
