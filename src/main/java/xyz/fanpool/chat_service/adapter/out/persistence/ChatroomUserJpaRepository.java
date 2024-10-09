package xyz.fanpool.chat_service.adapter.out.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatroomUserJpaRepository extends Repository<ChatroomUserJpaEntity, Long> {

    void save(ChatroomUserJpaEntity entity);

    @Modifying
    @Query("UPDATE ChatroomUserJpaEntity c SET c.lastActivityTime = :time WHERE c.userId = :userId AND c.chatroom.id = :chatroomId")
    void updateLastActivityTime(@Param("userId") long userId, @Param("chatroomId") long chatroomId, @Param("time") LocalDateTime time);

    boolean existsByUserIdAndChatroomId(long userId, long roomId);

    @Modifying
    void deleteByUserIdAndChatroomId(long userId, long roomId);

    @Query("SELECT cu FROM ChatroomUserJpaEntity cu JOIN FETCH ChatroomJpaEntity cr ON cu.chatroom.id = cr.id WHERE cu.userId = :userId")
    List<ChatroomUserJpaEntity> findByUserIdWithAssociatedChatroom(@Param("userId") Long userId);
}
