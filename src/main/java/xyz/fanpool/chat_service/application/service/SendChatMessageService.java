package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.application.dto.UserProfileDto;
import xyz.fanpool.chat_service.application.port.in.SendChatMessageUseCase;
import xyz.fanpool.chat_service.application.port.out.FindUserConnectedServerAddressPort;
import xyz.fanpool.chat_service.application.port.out.FindUserProfilePort;
import xyz.fanpool.chat_service.application.port.out.SaveChatMessagePort;
import xyz.fanpool.chat_service.application.port.out.SendPushNotificationPort;
import xyz.fanpool.chat_service.application.port.out.TransferChatMessagePort;
import xyz.fanpool.chat_service.application.service.event.StompMessageSendRequestedEvent;
import xyz.fanpool.chat_service.common.util.IdUtil;
import xyz.fanpool.chat_service.common.util.NetworkUtil;
import xyz.fanpool.chat_service.domain.ChatMessage;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendChatMessageService implements SendChatMessageUseCase {

	private final FindUserProfilePort findUserProfilePort;
	private final SaveChatMessagePort saveChatMessagePort;
	private final TransferChatMessagePort transferChatMessagePort;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final SendPushNotificationPort sendPushNotificationPort;
	private final FindUserConnectedServerAddressPort findUserConnectedServerAddressPort;

	@Override
	public void doService(StompChatMessage stompChatMessage, long receiverId) {

		// 채팅 메시지 저장
		ChatMessage chatMessage = createChatMessage(stompChatMessage);
		saveChatMessagePort.save(chatMessage);

		// 상대방이 접속한 채팅 서버 IP 주소
		String targetServerIpAddress = findUserConnectedServerAddressPort.findUserConnectedServerIpAddress(receiverId);

		// 다른 서버에 접속한 경우 메세지 전달
		if (targetServerIpAddress != null) {
			if (!NetworkUtil.getIpAddress()
					.equals(targetServerIpAddress)) {
				boolean success = transferChatMessagePort.doService(stompChatMessage, targetServerIpAddress);
				// 실패한 경우: PUSH 알림 전송
				if (!success) {
					sendPushNotification(chatMessage, receiverId);
				}
			}
		}
		// 접속하지 않은 경우 PUSH 알림 전송
		else {
			sendPushNotification(chatMessage, receiverId);
		}

		// 현재 서버와 연결된 채팅방 회원에게 메시지 전송
		StompMessageSendRequestedEvent event = StompMessageSendRequestedEvent.builder()
				.destination("/chat/sub/" + stompChatMessage.getRoomId())
				.message(stompChatMessage)
				.build();

		applicationEventPublisher.publishEvent(event);
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

	private void sendPushNotification(ChatMessage chatMessage, long receiverId) {
		UserProfileDto userProfileDto = findUserProfilePort.doService(chatMessage.getUserId());
		sendPushNotificationPort.sendNotification(receiverId, userProfileDto.nickname(), chatMessage.getContentPreview());
	}
}
