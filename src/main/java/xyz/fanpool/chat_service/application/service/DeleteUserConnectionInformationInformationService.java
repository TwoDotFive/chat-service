package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.application.port.in.DeleteUserConnectionInformationUseCase;
import xyz.fanpool.chat_service.application.port.out.DeleteUserConnectedServerAddressPort;

@Service
@RequiredArgsConstructor
public class DeleteUserConnectionInformationInformationService implements DeleteUserConnectionInformationUseCase {

	private final DeleteUserConnectedServerAddressPort deleteUserConnectedServerAddressPort;

	@Override
	@Transactional
	public void doService(long userId) {
		deleteUserConnectedServerAddressPort.delete(userId);
	}
}
