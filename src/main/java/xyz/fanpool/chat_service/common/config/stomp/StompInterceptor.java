package xyz.fanpool.chat_service.common.config.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.fanpool.chat_service.application.dto.UserProfileDto;
import xyz.fanpool.chat_service.adapter.out.client.UserServiceClient;
import xyz.fanpool.chat_service.application.dto.ChatroomUsersInfo;
import xyz.fanpool.chat_service.application.port.in.*;
import xyz.fanpool.chat_service.common.util.JwtUtil;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;
    private final FindChatroomUsersQuery findChatroomUsersQuery;
    private final UpdateChatroomUserConnectionStatusUseCase updateChatroomUserConnectionStatusUseCase;
    private final SubscribeChatroomTopicUseCase subscribeChatroomTopicUseCase;
    private final SubscribeChatroomListTopicUseCase subscribeChatroomListTopicUseCase;
    private final UpdateChatroomUserLastActivityTimeUseCase updateChatroomUserLastActivityTimeUseCase;
    private final UnsubscribeChatroomTopicUseCase unsubscribeChatroomTopicUseCase;
    private final UnsubscribeChatroomListTopicUseCase unsubscribeChatroomListTopicUseCase;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();
        if (command == null) throw new RuntimeException("No Stomp Command");

        switch (command) {
            case CONNECT -> connect(accessor);
            case SUBSCRIBE -> subscribe(accessor);
            case SEND -> send(accessor);
            case UNSUBSCRIBE, DISCONNECT -> disconnect(accessor);
        }

        return message;
    }

    // 최초 연결 시, 세션 속성에 사용자 ID 설정
    private void connect(StompHeaderAccessor accessor) {
        long userId = extractUserIdFromJwt(accessor);

        UserProfileDto userProfileDto = userServiceClient.findProfile(userId);

        addSessionAttribute(accessor, "userId", Long.valueOf(userProfileDto.id()));
        addSessionAttribute(accessor, "userNickname", userProfileDto.nickname());
    }

    private void subscribe(StompHeaderAccessor accessor) {
        String destination = Objects.requireNonNull(accessor.getDestination());
        Long userId = (Long) getSessionAttribute(accessor, "userId");

        // 채팅방 구독 시
        if (destination.startsWith("/chat/sub")) {

            long roomId = parseDestinationId(accessor, "/chat/sub");

            ChatroomUsersInfo chatroomUsersInfo;

            try {
                chatroomUsersInfo = findChatroomUsersQuery.doService(roomId);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Chatroom Not Found");
            }

            // 인가 처리
            if (!userId.equals(chatroomUsersInfo.hostUserId()) && !userId.equals(chatroomUsersInfo.guestUserId())) {
                throw new RuntimeException("Unauthorized Subscription Request");
            }

            Long receiverId = userId.equals(chatroomUsersInfo.hostUserId()) ? chatroomUsersInfo.guestUserId() : chatroomUsersInfo.hostUserId();

            // 세션 속성에 구독한 채팅방 ID 설정
            addSessionAttribute(accessor, "roomId", roomId);

            // 세션 속성에 구독한 상대방 ID 설정
            addSessionAttribute(accessor, "receiverId", receiverId);

            // 회원 채팅방 접속 상태 설정 : ON
            updateChatroomUserConnectionStatusUseCase.doService(userId, roomId, true);

            // 세션 속성 설정 : 구독 타입
            addSessionAttribute(accessor, "subscribe_type", "chatroom");

            // Redis Channel 구독 처리
            subscribeChatroomTopicUseCase.doService(userId, roomId);
        }

        // 회원 채팅방 목록 구독 시
        else if (destination.startsWith("/chatroom/sub")) {

            // 인가 처리
            Long destinationId = parseDestinationId(accessor, "/chatroom/sub");
            if (!destinationId.equals(userId)) {
                throw new RuntimeException("Unauthorized Subscription Request");
            }

            // 세션 속성 설정 : 구독 타입
            addSessionAttribute(accessor, "subscribe_type", "chatroom_list");

            // Redis Channel 구독 처리
            subscribeChatroomListTopicUseCase.doService(userId);
        }
    }

    private void disconnect(StompHeaderAccessor accessor) {

        Long userId = (Long) getSessionAttribute(accessor, "userId");
        String subscribeType = (String) getSessionAttribute(accessor, "subscribe_type");

        if (subscribeType == null) return;

        // 채팅방 구독 해지 시
        if (subscribeType.equals("chatroom")) {

            Long roomId = (Long) getSessionAttribute(accessor, "roomId");

            // 채팅방 퇴장 시간 기록
            updateChatroomUserLastActivityTimeUseCase.doService(userId, roomId);

            // 회원 채팅방 접속 상태 설정 : OFF
            updateChatroomUserConnectionStatusUseCase.doService(userId, roomId, false);

            // Redis Channel 구독 해지 처리
            unsubscribeChatroomTopicUseCase.doService(userId, roomId);
        }

        // 회원 채팅방 목록 구독 해지 시
        else if (subscribeType.equals("chatroom_list")) {

            // Redis Channel 구독 해지 처리
            unsubscribeChatroomListTopicUseCase.doService(userId);
        }
    }

    private void send(StompHeaderAccessor accessor) {
        Long destinationRoomId = parseDestinationId(accessor, "/chat/pub");
        Long connectedRoomId = (Long) getSessionAttribute(accessor, "roomId");

        // 채팅 전송 시, 목적지가 세션 속성에 설정된 채팅방 ID와 일치하는지 검증
        if (!destinationRoomId.equals(connectedRoomId)) {
            throw new RuntimeException("Unauthorized Send Request");
        }
    }

    private long extractUserIdFromJwt(StompHeaderAccessor accessor) {
        String bearerToken = accessor.getFirstNativeHeader("Authorization");
        if (!StringUtils.hasLength(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            log.info("Invalid Bearer Token Format");
        }
        String accessToken = bearerToken.substring(7);
        if (!jwtUtil.isTokenValid(accessToken)) {
            throw new RuntimeException();
        }
        return jwtUtil.getUserId(accessToken);
    }

    private Long parseDestinationId(StompHeaderAccessor accessor, String destinationPrefix) {
        String destination = accessor.getDestination();
        if (destination == null || destination.length() <= destinationPrefix.length() + 1) {
            log.info("Room Id Not Passed");
            throw new RuntimeException();
        }
        return Long.parseLong(destination.substring(destinationPrefix.length() + 1));
    }

    private Object getSessionAttribute(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null) {
            log.info("SessionAttribute `{}` is Null", key);
            throw new RuntimeException();
        }
        return sessionAttributes.getOrDefault(key, null);
    }

    private <T> void addSessionAttribute(StompHeaderAccessor accessor, String key, T value) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null) {
            log.error("SessionAttributes Not Found");
            throw new RuntimeException();
        }
        sessionAttributes.put(key, value);
    }

}
