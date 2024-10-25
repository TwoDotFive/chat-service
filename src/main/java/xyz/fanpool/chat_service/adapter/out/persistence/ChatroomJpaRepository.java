package xyz.fanpool.chat_service.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomJpaEntity, Long> {

    @Modifying
    @Query("UPDATE ChatroomJpaEntity cr SET cr.lastMessage = :lastMessage WHERE cr.id = :roomId")
    void updateLastMessage(@Param("roomId") long roomId, @Param("lastMessage") ChatMessagePreviewJpaValueType lastMessage);

}
