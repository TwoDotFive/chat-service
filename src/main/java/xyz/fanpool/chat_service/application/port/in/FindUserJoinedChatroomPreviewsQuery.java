package xyz.fanpool.chat_service.application.port.in;

import xyz.fanpool.chat_service.application.dto.ChatroomPreview;

import java.util.List;

public interface FindUserJoinedChatroomPreviewsQuery {

    List<ChatroomPreview> doService(long userId);
}
