package xyz.fanpool.chat_service.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import xyz.fanpool.chat_service.domain.ChatMessage;
import xyz.fanpool.chat_service.domain.ChatMessageType;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageJpaEntity implements Persistable<Long> {

    @Id
    private Long id;

    private Long roomId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private ChatMessageType type;

    private String content;

    @CreatedDate
    private LocalDateTime time;

    public ChatMessage toDomainEntity() {
        return ChatMessage.builder()
                .id(id)
                .roomId(roomId)
                .userId(userId)
                .type(type)
                .content(content)
                .time(time)
                .build();
    }

    public static ChatMessageJpaEntity of(ChatMessage chatMessage) {
        ChatMessageJpaEntity mongoEntity = new ChatMessageJpaEntity();
        mongoEntity.id = chatMessage.getId();
        mongoEntity.roomId = chatMessage.getRoomId();
        mongoEntity.userId = chatMessage.getUserId();
        mongoEntity.type = chatMessage.getType();
        mongoEntity.content = chatMessage.getContent();
        mongoEntity.time = chatMessage.getTime();
        return mongoEntity;
    }

    @Override
    public boolean isNew() {
        return time == null;
    }
}
