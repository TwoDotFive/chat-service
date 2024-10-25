package xyz.fanpool.chat_service.adapter.in.grpc;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ChatServiceGrpcServer {

	private final int port;
	private final ChatServiceGrpcImpl grpcChatService;

	private Server server;

	@Autowired
	public ChatServiceGrpcServer(@Value("${grpc.server.port}") int port, ChatServiceGrpcImpl grpcChatService) {
		this.port = port;
		this.grpcChatService = grpcChatService;
	}

	@PostConstruct
	public void postConstruct() throws IOException {
		start();
		log.info("server status : {}", server.isTerminated());
	}

	@PreDestroy
	public void preDestroy() throws InterruptedException {
		stop();
	}

	private void start() throws IOException {
		log.info("grpc server started");

		server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
				.addService(grpcChatService)
				.build()
				.start();

		log.info("Grpc server started, listening on {}", port);
		Runtime.getRuntime()
				.addShutdownHook(new Thread(() -> {
					System.err.println("*** shutting down gRPC server since JVM is shutting down");
					try {
						ChatServiceGrpcServer.this.stop();
					} catch (InterruptedException e) {
						e.printStackTrace(System.err);
					}
					System.err.println("*** server shut down");
				}));
	}

	private void stop() throws InterruptedException {
		if (server != null) {
			server.shutdown()
					.awaitTermination(30, TimeUnit.SECONDS);
		}
	}
}
