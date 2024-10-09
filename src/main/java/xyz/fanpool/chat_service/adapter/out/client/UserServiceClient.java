package xyz.fanpool.chat_service.adapter.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.fanpool.chat_service.application.dto.UserProfileDto;

@FeignClient(name = "user-service", url = "${url.main-service}")
public interface UserServiceClient {

    @GetMapping("/admin/user/profile/{userId}")
    UserProfileDto findProfile(@PathVariable(name = "userId") long userId);
}
