package com.chatbotservices.controller;

import com.chatbotservices.dto.ConversationDto;
import com.chatbotservices.model.Conversation;
import com.chatbotservices.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private static final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/save")
    public ResponseEntity<?> saveConversations(@RequestParam Long userId, @RequestBody ConversationDto conversationDto) {
        try {
            logger.info("Saving conversations for user: {}", userId);
            
            List<Conversation> savedConversations = conversationService.saveUserAndBotConversations(userId, conversationDto.getMessages());

            // Convert saved conversations to DTOs for response
            List<ConversationDto> responseDtos = savedConversations.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responseDtos);
        } catch (Exception e) {
            logger.error("Failed to save conversations for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to save conversations: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserConversations(@PathVariable Long userId) {
        try {
            logger.info("Fetching conversations for user: {}", userId);
            List<Conversation> conversations = conversationService.getConversationsByUserId(userId);
            if (conversations.isEmpty()) {
                return ResponseEntity.ok("No conversations found for user " + userId);
            }
            
            List<ConversationDto> conversationDtos = conversations.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(conversationDtos);
        } catch (Exception e) {
            logger.error("Error fetching conversations for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error fetching conversations: " + e.getMessage());
        }
    }

    private ConversationDto convertToDto(Conversation conversation) {
        ConversationDto dto = new ConversationDto();
        dto.setId(conversation.getId());
        dto.setMessages(List.of(conversation.getMessage()));
        dto.setTimestamp(conversation.getTimestamp());
        return dto;
    }
}
