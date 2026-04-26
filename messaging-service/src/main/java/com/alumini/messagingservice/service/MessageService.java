package com.alumini.messagingservice.service;

import com.alumini.messagingservice.client.UserServiceClient;
import com.alumini.messagingservice.dto.MentorshipDTO;
import com.alumini.messagingservice.dto.MessageRequest;
import com.alumini.messagingservice.dto.MessageResponse;
import com.alumini.messagingservice.dto.UserDTO;
import com.alumini.messagingservice.entity.Conversation;
import com.alumini.messagingservice.entity.Message;
import com.alumini.messagingservice.repository.ConversationRepository;
import com.alumini.messagingservice.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;
    public UserServiceClient userServiceClient;
    public ConversationRepository conversationRepository;

    public MessageService(MessageRepository messageRepository, RestTemplate restTemplate,
                          ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
        this.conversationRepository = conversationRepository;
    }

    public MessageResponse sendMessage(MessageRequest request) {

        Long studentId = request.getStudentId();
        Long alumniId = request.getAlumniId();

        if (studentId == null || alumniId == null) {
            throw new RuntimeException("Student ID and Alumni ID are required");
        }

        MentorshipDTO mentorship = getMentorshipFromMentorshipService(studentId, alumniId);

        if (mentorship == null ||
                (!"ACCEPTED".equalsIgnoreCase(mentorship.getStatus())
                        && !"ACTIVE".equalsIgnoreCase(mentorship.getStatus()))) {
            throw new RuntimeException("Messaging is allowed only after mentorship acceptance");
        }

        Conversation conversation = conversationRepository
                .findByUserOneIdAndUserTwoId(request.getSenderId(), request.getReceiverId())
                .orElseGet(() -> conversationRepository
                        .findByUserTwoIdAndUserOneId(request.getSenderId(), request.getReceiverId())
                        .orElseGet(() -> {
                            Conversation newConv = new Conversation();
                            newConv.setUserOneId(request.getSenderId());
                            newConv.setUserTwoId(request.getReceiverId());
                            newConv.setCreatedAt(LocalDateTime.now().toString());
                            return conversationRepository.save(newConv);
                        }));

        Message message = new Message();
        message.setConversationId(conversation.getId());
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

    public UserDTO getUserFromUserService(Long userId) {
        String url = "http://USER-SERVICE/users/" + userId;
        return restTemplate.getForObject(url, UserDTO.class);
    }

    public MessageResponse sendDirectMessage(Long senderId, Long receiverId, String content) {

        if (!userServiceClient.userExists(senderId)) {
            throw new RuntimeException("Sender does not exist in User Service");
        }

        if (!userServiceClient.userExists(receiverId)) {
            throw new RuntimeException("Receiver does not exist in User Service");
        }


        Conversation conversation = conversationRepository
                .findByUserOneIdAndUserTwoId(senderId, receiverId)
                .orElseGet(() -> conversationRepository
                        .findByUserTwoIdAndUserOneId(senderId, receiverId)
                        .orElseGet(() -> {
                            Conversation newConversation = new Conversation();
                            newConversation.setUserOneId(senderId);
                            newConversation.setUserTwoId(receiverId);
                            newConversation.setCreatedAt(LocalDateTime.now().toString());
                            return conversationRepository.save(newConversation);
                        }));

        Message message = new Message();
        message.setConversationId(conversation.getId());
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now().toString());
        message.setRead(false);

        Message saved = messageRepository.save(message);
        return mapToResponse(saved);
    }

    public MentorshipDTO getMentorshipFromMentorshipService(Long studentId, Long alumniId) {
        String url = "http://MENTORSHIP-SERVICE/mentorship/" + studentId + "/getMentorshipById/" + alumniId;
        return restTemplate.getForObject(url, MentorshipDTO.class);
    }
}