package xyz.fanpool.chat_service.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.fanpool.chat_service.adapter.in.dto.ChatMessageView;
import xyz.fanpool.chat_service.application.dto.ChatroomPreview;
import xyz.fanpool.chat_service.application.dto.FindChatRoomMessagesCommand;
import xyz.fanpool.chat_service.application.port.in.CreateChatroomUseCase;
import xyz.fanpool.chat_service.application.port.in.FindChatroomMessagesQuery;
import xyz.fanpool.chat_service.application.port.in.FindUserJoinedChatroomPreviewsQuery;
import xyz.fanpool.chat_service.application.port.in.LeaveChatRoomUseCase;
import xyz.fanpool.chat_service.common.UserPrincipal;
import xyz.fanpool.chat_service.common.aspect.Authenticated;
import xyz.fanpool.chat_service.adapter.in.dto.CreateChatroomRequest;
import xyz.fanpool.chat_service.adapter.in.dto.IdResponse;
import xyz.fanpool.chat_service.application.dto.CreateChatroomCommand;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

    private final LeaveChatRoomUseCase leaveChatRoomUseCase;
    private final CreateChatroomUseCase createChatroomUseCase;
    private final FindChatroomMessagesQuery findChatroomMessagesQuery;
    private final FindUserJoinedChatroomPreviewsQuery findUserJoinedChatroomPreviewsQuery;

    @Authenticated
    @PostMapping("/room")
    public ResponseEntity<IdResponse> createRoom(
            @RequestBody CreateChatroomRequest request,
            UserPrincipal userPrincipal
    ) {
        CreateChatroomCommand command = request.buildCommand(userPrincipal.id());
        long chatroomId = createChatroomUseCase.doService(command);
        return ResponseEntity.ok(IdResponse.build(chatroomId));
    }

    @Authenticated
    @GetMapping("/room/{roomId}/message")
    public ResponseEntity<List<ChatMessageView>> findChatMessages(
            @PathVariable long roomId,
            @RequestParam(name = "lastId", defaultValue = "" + Long.MAX_VALUE) long lastMessageId,
            @RequestParam(name = "size", defaultValue = "30") int pageSize,
            UserPrincipal userDetails
    ) {

        FindChatRoomMessagesCommand command = new FindChatRoomMessagesCommand(
                userDetails.id(), roomId, lastMessageId, pageSize);

        List<ChatMessageView> response = findChatroomMessagesQuery.doService(command)
                .stream()
                .map(ChatMessageView::of)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Authenticated
    @GetMapping("/room")
    public ResponseEntity<List<ChatroomPreview>> findRoomList(UserPrincipal userPrincipal) {

        List<ChatroomPreview> response = findUserJoinedChatroomPreviewsQuery.doService(userPrincipal.id());

        return ResponseEntity.ok(response);
    }

    @Authenticated
    @DeleteMapping("/room")
    public ResponseEntity<Void> exitChatroom(
            @RequestParam(name = "id") long roomId,
            UserPrincipal userPrincipal
    ) {
        leaveChatRoomUseCase.doService(userPrincipal.id(), roomId);
        return ResponseEntity.ok().build();
    }
}
