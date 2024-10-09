package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.SaveDeviceTokenUseCase;
import xyz.fanpool.chat_service.application.port.out.SaveDeviceTokenPort;

@Service
@RequiredArgsConstructor
public class SaveDeviceTokenService implements SaveDeviceTokenUseCase {

    private final SaveDeviceTokenPort saveDeviceTokenPort;

    @Override
    @Transactional
    public void doService(long userId, String token) {
        saveDeviceTokenPort.save(userId, token);
    }
}
