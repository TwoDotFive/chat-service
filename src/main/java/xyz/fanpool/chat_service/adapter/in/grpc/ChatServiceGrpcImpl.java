package xyz.fanpool.chat_service.adapter.in.grpc;

import com.example.grpctest.grpc.ChatServiceGrpc;
import com.example.grpctest.grpc.SendChatMessageRequest;
import com.example.grpctest.grpc.SendChatMessageResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.domain.ChatMessageType;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatServiceGrpcImpl extends ChatServiceGrpc.ChatServiceImplBase {

	private final SimpMessageSendingOperations simpMessageSendingOperations;

	@Override
	public void send(SendChatMessageRequest request, StreamObserver<SendChatMessageResponse> responseObserver) {
		String result;

		try {
			StompChatMessage stompChatMessage = StompChatMessage.builder()
					.messageId(request.getMessageId())
					.roomId(request.getRoomId())
					.senderId(request.getSenderId())
					.type(ChatMessageType.valueOf(request.getType()))
					.content(request.getContent())
					.build();

			simpMessageSendingOperations.convertAndSend("/chat/sub/" + request.getRoomId(), stompChatMessage);
			result = "success";
		} catch (Throwable e) {
			result = "error";
			log.error("gRPC Message Transfer Failed", e);
		}

		responseObserver.onNext(SendChatMessageResponse.newBuilder()
				.setResult(result)
				.build());

		responseObserver.onCompleted();
	}
}
