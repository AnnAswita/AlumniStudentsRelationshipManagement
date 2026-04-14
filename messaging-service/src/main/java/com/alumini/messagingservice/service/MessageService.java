package com.alumini.messagingservice.service;

import com.alumini.messagingservice.dto.MessageRequest;
import com.alumini.messagingservice.dto.MessageResponse;
import com.alumini.messagingservice.entity.Message;
import com.alumini.messagingservice.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageResponse sendMessage(MessageRequest request) {
        Message message = new Message();
        message.setConversationId(request.getConversationId());
        message.setSenderId(request.getSenderId());
        message.setReceiverId(request.getReceiverId());
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now().toString());
        message.setRead(false);

        Message saved = messageRepository.save(message);
        return mapToResponse(saved);
    }

    public List<MessageResponse> getMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponse markAsRead(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        Message updated = messageRepository.save(message);
        return mapToResponse(updated);
    }

    public List<MessageResponse> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverIdAndReadFalse(receiverId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MessageResponse mapToResponse(Message message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setConversationId(message.getConversationId());
        response.setSenderId(message.getSenderId());
        response.setReceiverId(message.getReceiverId());
        response.setContent(message.getContent());
        response.setTimestamp(message.getTimestamp());
        response.setRead(message.isRead());
        return response;
    }
}