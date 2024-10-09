package xyz.fanpool.chat_service.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import xyz.fanpool.chat_service.domain.ChatroomUser;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "chatroom_user")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomUserJpaEntity implements Persistable<Long> {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatroomJpaEntity chatroom;

    private Long userId;

    @CreatedDate
    private LocalDateTime createdTime;

    private LocalDateTime lastActivityTime;

    @Builder
    public static ChatroomUserJpaEntity of(ChatroomUser chatroomUser, ChatroomJpaEntity chatroomJpaEntity) {
        ChatroomUserJpaEntity chatroomUserJpaEntity = new ChatroomUserJpaEntity();
        chatroomUserJpaEntity.id = chatroomUser.getId();
        chatroomUserJpaEntity.chatroom = chatroomJpaEntity;
        chatroomUserJpaEntity.userId = chatroomUser.getUserId();
        chatroomUserJpaEntity.lastActivityTime = chatroomUser.getLastActivityTime();
        return chatroomUserJpaEntity;
    }

    @Override
    public boolean isNew() {
        return createdTime == null;
    }
}
