package xyz.fanpool.chat_service.adapter.in.dto;

public record IdResponse(String id) {

    public static IdResponse build(long id) {
        return new IdResponse(String.valueOf(id));
    }
}
