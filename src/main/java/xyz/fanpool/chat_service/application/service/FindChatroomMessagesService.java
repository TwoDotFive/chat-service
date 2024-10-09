package xyz.fanpool.chat_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fanpool.chat_service.domain.ChatMessage;
import xyz.fanpool.chat_service.application.dto.FindChatRoomMessagesCommand;
import xyz.fanpool.chat_service.application.port.in.FindChatroomMessagesQuery;
import xyz.fanpool.chat_service.application.port.out.CheckMemberOfChatroomPort;
import xyz.fanpool.chat_service.application.port.out.FindChatMessagesPort;
import xyz.fanpool.chat_service.common.exception.CustomException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindChatroomMessagesService implements FindChatroomMessagesQuery {

    private final FindChatMessagesPort findChatMessagesPort;
    private final CheckMemberOfChatroomPort checkMemberOfChatroomPort;

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> doService(FindChatRoomMessagesCommand command) {

        if (!checkMemberOfChatroomPort.doService(command.userId(), command.roomId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Illegal Request");
        }

        return findChatMessagesPort.findChatroomMessages(command.roomId(), command.lastMessageId(), command.pageSize());
    }
}
