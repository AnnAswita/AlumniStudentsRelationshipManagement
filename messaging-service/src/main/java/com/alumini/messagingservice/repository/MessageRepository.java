package com.alumini.messagingservice.repository;

import com.alumini.messagingservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationId(Long conversationId);

    List<Message> findByReceiverIdAndReadFalse(Long receiverId);
}
