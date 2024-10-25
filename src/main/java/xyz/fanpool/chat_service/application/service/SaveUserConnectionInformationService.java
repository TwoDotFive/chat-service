package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.SaveUserConnectionInformationUseCase;
import xyz.fanpool.chat_service.application.port.out.SaveUserConnectedServerAddressPort;
import xyz.fanpool.chat_service.common.util.NetworkUtil;

@Service
@RequiredArgsConstructor
public class SaveUserConnectionInformationService implements SaveUserConnectionInformationUseCase {

	private final SaveUserConnectedServerAddressPort saveUserConnectedServerAddressPort;

	@Override
	@Transactional
	public void doService(long userId) {
		saveUserConnectedServerAddressPort.saveUserConnectedServerAddress(userId, NetworkUtil.getIpAddress());
	}
}
