package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.out.SaveChatMessagePort;
import xyz.fanpool.chat_service.application.port.out.UpdateChatroomLastMessagePort;
import xyz.fanpool.chat_service.domain.ChatMessage;
import xyz.fanpool.chat_service.application.dto.SaveChatMessageCommand;
import xyz.fanpool.chat_service.application.port.in.SaveChatMessageUseCase;
import xyz.fanpool.chat_service.common.util.IdUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaveChatMessageService implements SaveChatMessageUseCase {

    private final SaveChatMessagePort saveChatMessagePort;
    private final UpdateChatroomLastMessagePort updateChatroomLastMessagePort;

    @Override
    @Transactional
    public void doService(SaveChatMessageCommand command) {

        ChatMessage chatMessage = createChatMessage(command);

        saveChatMessagePort.doService(chatMessage);

        updateChatroomLastMessagePort.doService(
                chatMessage.getRoomId(),
                chatMessage.getContentPreview(),
                chatMessage.getTime());
    }

    private ChatMessage createChatMessage(SaveChatMessageCommand command) {
        return ChatMessage.builder()
                .id(IdUtil.create())
                .roomId(command.roomId())
                .userId(command.senderId())
                .type(command.type())
                .content(command.content())
                .time(LocalDateTime.now())
                .build();
    }
}
