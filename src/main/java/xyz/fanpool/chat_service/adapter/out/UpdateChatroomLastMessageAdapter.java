package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatMessagePreviewJpaValueType;
import xyz.fanpool.chat_service.adapter.out.persistence.ChatroomJpaRepository;
import xyz.fanpool.chat_service.application.port.out.UpdateChatroomLastMessagePort;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UpdateChatroomLastMessageAdapter implements UpdateChatroomLastMessagePort {

    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    public void doService(long roomId, String content, LocalDateTime time) {
        ChatMessagePreviewJpaValueType chatMessagePreviewJpaValueType = new ChatMessagePreviewJpaValueType(content, time);
        chatroomJpaRepository.updateLastMessage(roomId, chatMessagePreviewJpaValueType);
    }
}
