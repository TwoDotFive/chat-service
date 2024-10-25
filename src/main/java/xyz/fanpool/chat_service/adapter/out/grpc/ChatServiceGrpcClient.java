package xyz.fanpool.chat_service.adapter.out.grpc;

import com.example.grpctest.grpc.ChatServiceGrpc;
import com.example.grpctest.grpc.ChatServiceGrpc.ChatServiceBlockingStub;
import com.example.grpctest.grpc.SendChatMessageRequest;
import com.example.grpctest.grpc.SendChatMessageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.application.dto.StompChatMessage;
import xyz.fanpool.chat_service.application.port.out.TransferChatMessagePort;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatServiceGrpcClient implements TransferChatMessagePort {

	@Value("${grpc.server.port}")
	private int port;

//	private final Map<String, Channel> stubMap = new ConcurrentHashMap<>();

	@Override
	public boolean doService(StompChatMessage message, String targetServerIpAddress) {

		ManagedChannel channel = ManagedChannelBuilder.forAddress(targetServerIpAddress, port)
				.usePlaintext()
				.build();

		ChatServiceBlockingStub stub = ChatServiceGrpc.newBlockingStub(channel);

		SendChatMessageResponse response = null;

		log.info("grpc client treid to send message");
		try {

			SendChatMessageRequest request = SendChatMessageRequest.newBuilder()
					.setMessageId(message.getMessageId())
					.setRoomId(message.getRoomId())
					.setSenderId(message.getSenderId())
					.setType(message.getType().name())
					.setContent(message.getContent())
					.build();

			response = stub.send(request);

		} catch (StatusRuntimeException e) {
			if (e.getStatus()
					.getCode() == Status.Code.DEADLINE_EXCEEDED) {
				log.error("GRPC TIME OUT : target {}", targetServerIpAddress);
				return false;
			}
		} catch (Throwable e) {
			log.error("GRPC ERROR : {}", e.getMessage());
			return false;
		}

		channel.shutdown();

		return response != null && "success".equals(response.getResult());
	}
}
