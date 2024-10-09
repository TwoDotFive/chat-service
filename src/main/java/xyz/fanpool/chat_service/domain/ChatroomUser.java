package xyz.fanpool.chat_service.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatroomUser {

    private long id;

    private long roomId;

    private long userId;

    private LocalDateTime lastActivityTime;

}
