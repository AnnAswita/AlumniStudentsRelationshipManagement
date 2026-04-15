package com.alumini.messagingservice.repository;

import com.alumini.messagingservice.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByUserOneIdAndUserTwoId(Long userOneId, Long userTwoId);

    Optional<Conversation> findByUserTwoIdAndUserOneId(Long userTwoId, Long userOneId);

    List<Conversation> findByUserOneIdOrUserTwoId(Long userOneId, Long userTwoId);
}
