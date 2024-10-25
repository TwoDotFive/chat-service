package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.domain.ChatMessage;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatMessageMongoEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatMessageMongoRepository;
import xyz.fanpool.chat_service.application.port.out.FindChatMessagesPort;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindChatMessagesAdapter implements FindChatMessagesPort {

    private final ChatMessageMongoRepository chatMessageMongoRepository;

    @Override
    public List<ChatMessage> doService(long roomId, long lastChatMessageId, int pageSize) {
        return chatMessageMongoRepository.findByPage(roomId, lastChatMessageId, pageSize)
                .stream()
                .map(ChatMessageMongoEntity::toDomainEntity)
                .toList();
    }
}
