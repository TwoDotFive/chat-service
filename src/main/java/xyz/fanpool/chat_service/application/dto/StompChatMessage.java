package xyz.fanpool.chat_service.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.fanpool.chat_service.domain.ChatMessageType;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StompChatMessage {

	private long messageId;
	private long roomId;
	private long senderId;
	private ChatMessageType type;
	private String content;

}
