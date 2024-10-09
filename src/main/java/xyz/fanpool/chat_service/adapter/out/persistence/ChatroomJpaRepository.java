package xyz.fanpool.chat_service.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomJpaEntity, Long> {
}
