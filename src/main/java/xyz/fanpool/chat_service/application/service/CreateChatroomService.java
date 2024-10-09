package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.adapter.out.client.FanpoolServiceClient;
import xyz.fanpool.chat_service.application.dto.CreateChatroomCommand;
import xyz.fanpool.chat_service.application.port.in.CreateChatroomUseCase;
import xyz.fanpool.chat_service.application.port.out.SaveChatroomPort;
import xyz.fanpool.chat_service.common.util.IdUtil;
import xyz.fanpool.chat_service.domain.Chatroom;
import xyz.fanpool.chat_service.domain.ChatroomUser;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateChatroomService implements CreateChatroomUseCase {

    private final SaveChatroomPort saveChatroomPort;
    private final FanpoolServiceClient fanpoolServiceClient;

    @Override
    @Transactional
    public long doService(CreateChatroomCommand command) {

        Chatroom chatroom = createChatroom(command);
        ChatroomUser hostChatroom = createChatroomUser(chatroom, command.hostUserId(), LocalDateTime.of(2024, 10, 1, 0, 0));
        ChatroomUser guestChatroom = createChatroomUser(chatroom, command.guestUserId(), LocalDateTime.now());

        saveChatroomPort.doService(chatroom, hostChatroom, guestChatroom);

        return chatroom.getId();
    }

    private Chatroom createChatroom(CreateChatroomCommand command) {

        String gameTeamNames = fanpoolServiceClient.findGameTeamsOfFanpool(command.fanpoolId());

        return Chatroom.create(
                command.fanpoolId(),
                command.hostUserId(),
                command.guestUserId(),
                gameTeamNames
        );
    }


    private ChatroomUser createChatroomUser(Chatroom chatroom, long userId, LocalDateTime lastActivityTime) {
        return ChatroomUser.builder()
                .id(IdUtil.create())
                .roomId(chatroom.getId())
                .userId(userId)
                .lastActivityTime(lastActivityTime)
                .build();
    }

}
