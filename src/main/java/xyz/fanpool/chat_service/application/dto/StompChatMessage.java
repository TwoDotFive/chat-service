package xyz.fanpool.chat_service.application.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.fanpool.chat_service.domain.ChatMessageType;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StompChatMessage {

    private long roomId;
    private long senderId;
    private long receiverId;
    private ChatMessageType type;
    private String content;
    private String nickname;

}
