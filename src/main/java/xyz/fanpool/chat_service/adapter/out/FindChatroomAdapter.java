package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.FindChatroomPort;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomJpaEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomJpaRepository;
import xyz.fanpool.chat_service.domain.Chatroom;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindChatroomAdapter implements FindChatroomPort {

    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    public Optional<Chatroom> doService(long roomId) {
        return chatroomJpaRepository.findById(roomId)
                .map(ChatroomJpaEntity::toDomainEntity);
    }
}
