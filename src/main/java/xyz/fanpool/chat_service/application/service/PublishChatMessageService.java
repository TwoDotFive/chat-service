package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.application.port.in.PublishChatMessageUseCase;
import xyz.fanpool.chat_service.application.port.out.CheckChatroomUserConnectedPort;
import xyz.fanpool.chat_service.application.port.out.PublishChatMessagePort;
import xyz.fanpool.chat_service.application.port.out.SaveChatMessagePort;
import xyz.fanpool.chat_service.application.port.out.SendPushNotificationPort;
import xyz.fanpool.chat_service.common.util.IdUtil;
import xyz.fanpool.chat_service.domain.ChatMessage;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PublishChatMessageService implements PublishChatMessageUseCase {

    private final SaveChatMessagePort saveChatMessagePort;
    private final PublishChatMessagePort publishChatMessagePort;
    private final CheckChatroomUserConnectedPort checkChatroomUserConnectedPort;
    private final SendPushNotificationPort sendPushNotificationPort;

    @Override
    public void doService(StompChatMessage stompChatMessage) {

        long receiverId = stompChatMessage.getReceiverId();
        long roomId = stompChatMessage.getRoomId();

        // 채팅 메시지 저장
        ChatMessage chatMessage = createChatMessage(stompChatMessage);
        saveChatMessagePort.save(chatMessage);

        // Redis Message Pub
        publishChatMessagePort.doService(stompChatMessage);

        // 상대방 미접속 시 PUSH 알림 전송
        if (!checkChatroomUserConnectedPort.checkUserConnected(receiverId, roomId)) {
            sendPushNotificationPort.sendNotification(receiverId,
                    stompChatMessage.getNickname(),
                    chatMessage.getContentPreview());
        }
    }

    private ChatMessage createChatMessage(StompChatMessage message) {
        return ChatMessage.builder()
                .id(IdUtil.create())
                .roomId(message.getRoomId())
                .userId(message.getSenderId())
                .type(message.getType())
                .content(message.getContent())
                .time(LocalDateTime.now())
                .build();
    }
}
