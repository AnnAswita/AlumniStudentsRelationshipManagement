package com.alumini.messagingservice.controller;

import com.alumini.messagingservice.dto.MessageRequest;
import com.alumini.messagingservice.dto.MessageResponse;
import com.alumini.messagingservice.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final MessageService messageService;

    public ChatWebSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public MessageResponse send(MessageRequest request) {
        return messageService.sendMessage(request);
    }
}


