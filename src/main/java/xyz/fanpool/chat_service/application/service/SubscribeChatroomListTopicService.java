package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.SubscribeChatroomListTopicUseCase;
import xyz.fanpool.chat_service.application.port.out.SubscribeChatroomListTopicPort;

@Service
@RequiredArgsConstructor
public class SubscribeChatroomListTopicService implements SubscribeChatroomListTopicUseCase {

    private static final String CHATROOM_LIST_TOPIC_PREFIX = "user-chatroom-list_";

    private final SubscribeChatroomListTopicPort subscribeChatroomListTopicPort;

    @Override
    @Transactional
    public void doService(long userId) {
        subscribeChatroomListTopicPort.subscribe(CHATROOM_LIST_TOPIC_PREFIX + userId);
    }
}
