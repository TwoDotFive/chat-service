package xyz.fanpool.chat_service.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageJpaEntity, Long> {

    List<ChatMessageJpaEntity> findByRoomIdAndIdLessThanOrderByIdDesc(long roomId, long lastId);

    ChatMessageJpaEntity findFirstByRoomIdOrderByIdDesc(long roomId);
}
