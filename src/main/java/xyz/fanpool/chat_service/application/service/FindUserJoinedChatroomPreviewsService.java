package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.dto.ChatroomPreview;
import xyz.fanpool.chat_service.application.dto.ChatroomUserInfo;
import xyz.fanpool.chat_service.application.dto.UserProfileDto;
import xyz.fanpool.chat_service.application.port.in.FindUserJoinedChatroomPreviewsQuery;
import xyz.fanpool.chat_service.application.port.out.FindChatroomLastMessagePort;
import xyz.fanpool.chat_service.application.port.out.FindUserJoinedChatroomsPort;
import xyz.fanpool.chat_service.application.port.out.FindUserProfilePort;
import xyz.fanpool.chat_service.domain.ChatMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindUserJoinedChatroomPreviewsService implements FindUserJoinedChatroomPreviewsQuery {

    private final FindUserProfilePort findUserProfilePort;
    private final FindUserJoinedChatroomsPort findUserJoinedChatroomsPort;
    private final FindChatroomLastMessagePort findChatroomLastMessagePort;

    @Override
    @Transactional(readOnly = true)
    public List<ChatroomPreview> doService(long userId) {

        List<ChatroomPreview> result = new ArrayList<>();

        for (ChatroomUserInfo chatroomUserInfo : findUserJoinedChatroomsPort.doService(userId)) {

            UserProfileDto partnerProfile = findUserProfilePort.doService(chatroomUserInfo.partnerId());

            ChatMessage lastChatMessage = findChatroomLastMessagePort.findChatroomLastMessage(chatroomUserInfo.roomId());

            ChatroomPreview chatroomPreview = ChatroomPreview.build(chatroomUserInfo, lastChatMessage, partnerProfile);
            result.add(chatroomPreview);
        }

        return result;
    }
}
