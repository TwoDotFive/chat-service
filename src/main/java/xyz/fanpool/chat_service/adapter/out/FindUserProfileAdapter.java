package xyz.fanpool.chat_service.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.fanpool.chat_service.adapter.out.client.UserServiceClient;
import xyz.fanpool.chat_service.application.dto.UserProfileDto;
import xyz.fanpool.chat_service.application.port.out.FindUserProfilePort;

@Component
@RequiredArgsConstructor
public class FindUserProfileAdapter implements FindUserProfilePort {

    private final UserServiceClient userServiceClient;

    @Override
    public UserProfileDto doService(long userId) {
        return userServiceClient.findProfile(userId);
    }
}
