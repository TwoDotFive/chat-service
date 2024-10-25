package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.port.out.SendPushNotificationPort;
import xyz.fanpool.chat_service.application.service.event.StompMessageSendRequestedEvent;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.application.port.out.CheckChatroomUserConnectedPort;

@Service
@RequiredArgsConstructor
public class ChatroomTopicMessageHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SendPushNotificationPort sendPushNotificationPort;
    private final CheckChatroomUserConnectedPort checkChatroomUserConnectedPort;

    public void doService(StompChatMessage chatMessage) {

        long receiverId = chatMessage.getReceiverId();
        long roomId = chatMessage.getRoomId();

        // 상대방의 채팅방 접속상태 확인
        if (checkChatroomUserConnectedPort.checkUserConnected(receiverId, roomId)) {
            // 접속한 경우, 소켓으로 메시지 전송
            applicationEventPublisher.publishEvent(StompMessageSendRequestedEvent.builder()
                    .destination("/chat/sub/" + roomId)
                    .message(chatMessage)
                    .build()
            );
        } else {
            // 접속하지 않은 경우, Push 알림 전송
            String message = switch (chatMessage.getType()) {
                case IMAGE -> "사진";
                case FANPOOL_LOG -> "팬풀 로그가 공유되었어요";
                default ->  chatMessage.getContent();
            };

            sendPushNotificationPort.sendNotification(receiverId, chatMessage.getNickname(), message);
        }
    }

}
