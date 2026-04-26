package com.alumini.messagingservice.service;

import org.springframework.stereotype.Service;

import com.alumini.messagingservice.client.UserServiceClient;
import com.alumini.messagingservice.dto.ConversationResponse;
import com.alumini.messagingservice.entity.Conversation;
import com.alumini.messagingservice.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    public class ConversationService {

        private final ConversationRepository conversationRepository;
        private final UserServiceClient userServiceClient;

        public ConversationService(ConversationRepository conversationRepository,
                                   UserServiceClient userServiceClient) {
            this.conversationRepository = conversationRepository;
            this.userServiceClient = userServiceClient;
        }

        public ConversationResponse startOrGetConversation(Long senderId, Long receiverId) {

            if (senderId == null || receiverId == null) {
                throw new RuntimeException("Sender ID and Receiver ID are required");
            }

            Conversation existing = conversationRepository
                    .findByUserOneIdAndUserTwoId(senderId, receiverId)
                    .orElseGet(() -> conversationRepository
                            .findByUserTwoIdAndUserOneId(senderId, receiverId)
                            .orElse(null));

            if (existing != null) {
                return mapToResponse(existing);
            }

            Conversation conversation = new Conversation();
            conversation.setUserOneId(senderId);
            conversation.setUserTwoId(receiverId);
            conversation.setCreatedAt(LocalDateTime.now().toString());

            Conversation saved = conversationRepository.save(conversation);
            return mapToResponse(saved);
        }

        public List<ConversationResponse> getUserConversations(Long userId) {
            return conversationRepository.findByUserOneIdOrUserTwoId(userId, userId)
                    .stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        private ConversationResponse mapToResponse(Conversation conversation) {
            ConversationResponse response = new ConversationResponse();
            response.setConversationId(conversation.getId());
            response.setUserOneId(conversation.getUserOneId());
            response.setUserTwoId(conversation.getUserTwoId());
            response.setCreatedAt(conversation.getCreatedAt());
            return response;
        }
    }

