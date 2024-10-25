package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.adapter.out.client.UserServiceClient;
import xyz.fanpool.chat_service.application.dto.ChatroomPreview;
import xyz.fanpool.chat_service.application.dto.ChatroomUserInfo;
import xyz.fanpool.chat_service.application.dto.UserProfileDto;
import xyz.fanpool.chat_service.application.port.in.FindUserJoinedChatroomPreviewsQuery;
import xyz.fanpool.chat_service.application.port.out.FindUserJoinedChatroomsPort;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindUserJoinedChatroomPreviewsService implements FindUserJoinedChatroomPreviewsQuery {

    private final UserServiceClient userServiceClient;
    private final FindUserJoinedChatroomsPort findUserJoinedChatroomsPort;

    @Override
    @Transactional(readOnly = true)
    public List<ChatroomPreview> doService(long userId) {

        List<ChatroomPreview> result = new ArrayList<>();

        for (ChatroomUserInfo chatroomUserInfo : findUserJoinedChatroomsPort.doService(userId)) {

            UserProfileDto partnerProfile = userServiceClient.findProfile(chatroomUserInfo.partnerId());

            ChatroomPreview chatroomPreview = ChatroomPreview.build(chatroomUserInfo, partnerProfile);
            result.add(chatroomPreview);
        }

        return result;
    }
}
