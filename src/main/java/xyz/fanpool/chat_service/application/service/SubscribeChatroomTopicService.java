package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.port.in.SubscribeChatroomTopicUseCase;
import xyz.fanpool.chat_service.application.port.out.SubscribeChatroomTopicPort;

@Service
@RequiredArgsConstructor
public class SubscribeChatroomTopicService implements SubscribeChatroomTopicUseCase {

    private static final String CHATROOM_TOPIC_PREFIX = "room_";

    private final SubscribeChatroomTopicPort subscribeChatroomTopicPort;

    @Override
    public void doService(long userId, long roomId) {
        subscribeChatroomTopicPort.subscribe(CHATROOM_TOPIC_PREFIX + roomId);
    }
}
