package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomJpaEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomJpaRepository;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomUserJpaRepository;
import xyz.fanpool.chat_service.application.port.out.SaveChatroomPort;
import xyz.fanpool.chat_service.domain.Chatroom;
import xyz.fanpool.chat_service.domain.ChatroomUser;

@Component
@RequiredArgsConstructor
public class SaveChatroomAdapter implements SaveChatroomPort {

    private final ChatroomJpaRepository chatroomJpaRepository;
    private final ChatroomUserJpaRepository chatroomUserJpaRepository;

    @Override
    @Transactional
    public void doService(Chatroom chatroom, ChatroomUser hostChatroom, ChatroomUser guestChatroom) {

        ChatroomJpaEntity chatroomJpaEntity = ChatroomJpaEntity.of(chatroom);
        chatroomJpaRepository.save(chatroomJpaEntity);

        ChatroomUserJpaEntity hostChatroomUserJpaEntity = ChatroomUserJpaEntity.of(hostChatroom, chatroomJpaEntity);
        chatroomUserJpaRepository.save(hostChatroomUserJpaEntity);

        ChatroomUserJpaEntity guestChatroomUserJpaEntity = ChatroomUserJpaEntity.of(guestChatroom, chatroomJpaEntity);
        chatroomUserJpaRepository.save(guestChatroomUserJpaEntity);
    }
}
