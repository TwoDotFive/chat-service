package xyz.fanpool.chat_service.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageMongoRepository {

    private final MongoTemplate mongoTemplate;

    private static final String CHAT_MESSAGE_DOCUMENT = "chat_message";

    public void save(ChatMessageMongoEntity message) {
        mongoTemplate.insert(message, CHAT_MESSAGE_DOCUMENT);
    }

    public List<ChatMessageMongoEntity> findByPage(Long roomId, Long lastId, int pageSize) {
        Query query = new Query(Criteria.where("chatRoomId").is(roomId).and("id").lt(lastId));
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.limit(pageSize);
        return mongoTemplate.find(query, ChatMessageMongoEntity.class, CHAT_MESSAGE_DOCUMENT);
    }

}
