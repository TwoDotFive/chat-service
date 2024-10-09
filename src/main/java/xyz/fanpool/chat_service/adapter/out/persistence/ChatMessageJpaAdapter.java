package xyz.fanpool.chat_service.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.port.out.FindChatMessagesPort;
import xyz.fanpool.chat_service.application.port.out.FindChatroomLastMessagePort;
import xyz.fanpool.chat_service.application.port.out.SaveChatMessagePort;
import xyz.fanpool.chat_service.domain.ChatMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageJpaAdapter implements
        FindChatMessagesPort,
        FindChatroomLastMessagePort,
        SaveChatMessagePort
{

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    @Override
    public List<ChatMessage> findChatroomMessages(long roomId, long lastChatMessageId, int pageSize) {
        return chatMessageJpaRepository.findByRoomIdAndIdLessThanOrderByIdDesc(roomId, lastChatMessageId)
                .stream()
                .map(ChatMessageJpaEntity::toDomainEntity)
                .limit(pageSize)
                .toList();
    }

    @Override
    public ChatMessage findChatroomLastMessage(long chatroomId) {
        ChatMessageJpaEntity jpaEntity = chatMessageJpaRepository.findFirstByRoomIdOrderByIdDesc(chatroomId);
        if (jpaEntity != null) return jpaEntity.toDomainEntity();
        return null;
    }

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageJpaRepository.save(ChatMessageJpaEntity.of(chatMessage));
    }
}