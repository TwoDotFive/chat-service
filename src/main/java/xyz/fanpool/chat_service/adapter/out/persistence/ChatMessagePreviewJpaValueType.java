package xyz.fanpool.chat_service.adapter.out.persistence;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.fanpool.chat_service.domain.ChatMessagePreview;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessagePreviewJpaValueType {

    private String content;

    private LocalDateTime time;

    public ChatMessagePreviewJpaValueType(String content, LocalDateTime time) {
        this.content = content;
        this.time = time;
    }

    public static ChatMessagePreviewJpaValueType of(ChatMessagePreview chatMessagePreview) {
        return new ChatMessagePreviewJpaValueType(chatMessagePreview.content(), chatMessagePreview.time());
    }

    public ChatMessagePreview toDomainEntity() {
        return new ChatMessagePreview(content, time);
    }
}
