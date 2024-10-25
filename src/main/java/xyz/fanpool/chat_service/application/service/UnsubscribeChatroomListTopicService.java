package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.UnsubscribeChatroomListTopicUseCase;
import xyz.fanpool.chat_service.application.port.out.UnsubscribeChatroomListTopicPort;

@Service
@RequiredArgsConstructor
public class UnsubscribeChatroomListTopicService implements UnsubscribeChatroomListTopicUseCase {

    private static final String CHATROOM_LIST_TOPIC_PREFIX = "user-chatroom-list_";

    private final UnsubscribeChatroomListTopicPort unsubscribeChatroomListTopicPort;

    @Override
    @Transactional
    public void doService(long userId) {
        unsubscribeChatroomListTopicPort.unsubscribe(CHATROOM_LIST_TOPIC_PREFIX + userId);
    }
}
