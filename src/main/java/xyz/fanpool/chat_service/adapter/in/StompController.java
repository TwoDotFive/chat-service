package xyz.fanpool.chat_service.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import xyz.fanpool.chat_service.application.dto.SaveChatMessageCommand;
import xyz.fanpool.chat_service.application.port.in.PublishChatMessageUseCase;
import xyz.fanpool.chat_service.application.port.in.SaveChatMessageUseCase;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final SaveChatMessageUseCase saveChatMessageUseCase;
    private final PublishChatMessageUseCase publishChatMessageUseCase;

    @MessageMapping("/{roomId}")
    public void chat(
            @DestinationVariable long roomId,
            StompChatMessage message,
            StompHeaderAccessor accessor
    ) {

        Long userId = (Long) Objects.requireNonNull(accessor.getSessionAttributes()).get("userId");
        Long partnerId = (Long) Objects.requireNonNull(accessor.getSessionAttributes()).get("receiverId");
        String userNickname = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("userNickname");

        message.setRoomId(roomId);
        message.setSenderId(userId);
        message.setNickname(userNickname);
        message.setReceiverId(partnerId);

        SaveChatMessageCommand saveChatMessageCommand =
                new SaveChatMessageCommand(roomId, userId, message.getType(), message.getContent());

        saveChatMessageUseCase.doService(saveChatMessageCommand);

        publishChatMessageUseCase.doService(message);
    }
}
