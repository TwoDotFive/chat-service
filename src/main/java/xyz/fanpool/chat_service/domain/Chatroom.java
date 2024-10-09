package xyz.fanpool.chat_service.domain;

import lombok.Builder;
import lombok.Getter;
import xyz.fanpool.chat_service.common.util.IdUtil;

@Getter
@Builder
public class Chatroom {

    private long id;

    private long fanpoolId;

    private long hostUserId;

    private long guestUserId;

    private String teams;

    private ChatMessagePreview lastMessage;

    public static Chatroom create(long fanpoolId, long hostUserId, long guestUserId, String teams) {
        return new Chatroom(
                IdUtil.create(),
                fanpoolId,
                hostUserId,
                guestUserId,
                teams,
                ChatMessagePreview.getInitialPreview()
        );
    }

}
