package xyz.fanpool.chat_service.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import xyz.fanpool.chat_service.common.entity.BaseTimeEntity;
import xyz.fanpool.chat_service.domain.Chatroom;

@Getter
@Entity
@Table(name = "chatroom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomJpaEntity extends BaseTimeEntity implements Persistable<Long> {

    @Id
    private Long id;

    private Long fanpoolId;

    // 팬풀 주최자 ID
    private Long hostUserId;

    // 팬풀 참여자 ID (= 채팅방 생성자, 채팅 메시지 최초 전송자)
    private Long guestUserId;

    // 팬풀 관련 경기 참여 팀 이름 (ex : 키움히어로즈 vs KIA타이거즈)
    private String teams;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "content", column = @Column(name = "last_message_content")),
            @AttributeOverride(name = "time", column = @Column(name = "last_message_time"))
    })
    private ChatMessagePreviewJpaValueType lastMessage;

    public static ChatroomJpaEntity of(Chatroom chatroom) {
        ChatroomJpaEntity chatroomJpaEntity = new ChatroomJpaEntity();
        chatroomJpaEntity.id = chatroom.getId();
        chatroomJpaEntity.fanpoolId = chatroom.getFanpoolId();
        chatroomJpaEntity.hostUserId = chatroom.getHostUserId();
        chatroomJpaEntity.guestUserId = chatroom.getGuestUserId();
        chatroomJpaEntity.teams = chatroom.getTeams();
        chatroomJpaEntity.lastMessage = ChatMessagePreviewJpaValueType.of(chatroom.getLastMessage());
        return chatroomJpaEntity;
    }

    public Chatroom toDomainEntity() {
        return Chatroom.builder()
                .id(id)
                .fanpoolId(fanpoolId)
                .hostUserId(hostUserId)
                .guestUserId(guestUserId)
                .teams(teams)
                .lastMessage(lastMessage.toDomainEntity())
                .build();
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
