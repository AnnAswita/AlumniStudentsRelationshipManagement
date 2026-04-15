package com.alumini.messagingservice.controller;

import com.alumini.messagingservice.dto.ConversationResponse;
import com.alumini.messagingservice.dto.StartConversationRequest;
import com.alumini.messagingservice.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
@CrossOrigin
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/start")
    public ConversationResponse startConversation(@RequestBody StartConversationRequest request) {
        return conversationService.startOrGetConversation(request.getSenderId(), request.getReceiverId());
    }

    @GetMapping("/user/{userId}")
    public List<ConversationResponse> getUserConversations(@PathVariable Long userId) {
        return conversationService.getUserConversations(userId);
    }
}
