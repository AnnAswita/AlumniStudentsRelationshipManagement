package com.alumini.messagingservice.controller;

import com.alumini.messagingservice.dto.MessageRequest;
import com.alumini.messagingservice.dto.MessageResponse;
import com.alumini.messagingservice.dto.SendDirectMessageRequest;
import com.alumini.messagingservice.entity.Message;
import com.alumini.messagingservice.repository.MessageRepository;
import com.alumini.messagingservice.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public MessageResponse sendMessage(@RequestBody MessageRequest request) {
        return messageService.sendMessage(request);
    }

    @GetMapping("/{conversationId}")
    public List<MessageResponse> getMessages(@PathVariable Long conversationId) {
        return messageService.getMessages(conversationId);
    }

    @PutMapping("/read/{id}")
    public MessageResponse markAsRead(@PathVariable Long id) {
        return messageService.markAsRead(id);
    }

    @GetMapping("/unread/{receiverId}")
    public List<MessageResponse> getUnreadMessages(@PathVariable Long receiverId) {
        return messageService.getUnreadMessages(receiverId);
    }

    @PostMapping("/send-direct")
    public MessageResponse sendDirectMessage(@RequestBody SendDirectMessageRequest request) {
        return messageService.sendDirectMessage(
                request.getSenderId(),
                request.getReceiverId(),
                request.getContent()
        );
    }
}