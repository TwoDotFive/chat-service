package xyz.fanpool.chat_service.adapter.in.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.application.port.in.SendChatMessageUseCase;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class StompController {

	private final SendChatMessageUseCase sendChatMessageUseCase;

	@MessageMapping("/{roomId}")
	public void chat(@DestinationVariable long roomId, StompChatMessage message, StompHeaderAccessor accessor) {

		Long senderId = (Long) Objects.requireNonNull(accessor.getSessionAttributes()).get("userId");
		Long receiverId = (Long) Objects.requireNonNull(accessor.getSessionAttributes()).get("receiverId");
		message.setRoomId(roomId);
		message.setSenderId(senderId);
		sendChatMessageUseCase.doService(message, receiverId);
	}
}
