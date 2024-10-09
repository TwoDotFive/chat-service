package xyz.fanpool.chat_service.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fanpool.chat_service.application.port.in.SaveDeviceTokenUseCase;
import xyz.fanpool.chat_service.common.UserPrincipal;
import xyz.fanpool.chat_service.common.aspect.Authenticated;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/notification/push")
public class PushNotificationController {

    private final SaveDeviceTokenUseCase saveDeviceTokenUseCase;

    @Authenticated
    @PostMapping("/token")
    public void saveToken(
            @RequestBody String token,
            UserPrincipal userDetails
    ) {
        saveDeviceTokenUseCase.doService(userDetails.id(), token);
    }
}
