package xyz.fanpool.chat_service.application.port.out;

import xyz.fanpool.chat_service.application.dto.UserProfileDto;

public interface FindUserProfilePort {

    UserProfileDto doService(long userId);
}
