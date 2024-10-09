package xyz.fanpool.chat_service.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessage {

    private long id;

    private long roomId;

    private long userId;

    private ChatMessageType type;

    private String content;

    private LocalDateTime time;

    public String getContentPreview() {
        return switch (type) {
            case IMAGE -> "사진";
            case FANPOOL_LOG -> "팬풀 로그가 공유되었어요";
            default -> content;
        };
    }

}
