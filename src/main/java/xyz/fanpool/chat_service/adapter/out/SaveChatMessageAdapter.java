package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatMessageMongoEntity;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatMessageMongoRepository;
import xyz.fanpool.chat_service.application.port.out.SaveChatMessagePort;
import xyz.fanpool.chat_service.domain.ChatMessage;

@Component
@RequiredArgsConstructor
public class SaveChatMessageAdapter implements SaveChatMessagePort {

    private final ChatMessageMongoRepository chatMessageMongoRepository;

    @Override
    public void doService(ChatMessage chatMessage) {
        chatMessageMongoRepository.save(ChatMessageMongoEntity.of(chatMessage));
    }
}
