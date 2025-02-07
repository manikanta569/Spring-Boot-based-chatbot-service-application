package com.chatbotservices.controller;

import com.chatbotservices.dto.ConversationDto;
import com.chatbotservices.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private static final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    private ConversationService conversationService;

    // Save conversations
    @PostMapping("/save")
    public ResponseEntity<?> saveConversations(@RequestParam Long userId, @RequestBody ConversationDto conversationDto) {
        if (conversationDto.getMessages() == null || conversationDto.getMessages().isEmpty()) {
            return ResponseEntity.badRequest().body("Messages cannot be empty.");
        }
        try {
            logger.info("Saving conversations for user: {}", userId);
            Long conversationId = conversationService.saveUserAndBotConversationsInSingleColumn(userId, conversationDto.getMessages());
            return ResponseEntity.ok("Conversation saved successfully with ID: " + conversationId);
        } catch (Exception e) {
            logger.error("Failed to save conversations for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to save conversations: " + e.getMessage());
        }
    }

    // Get user conversations
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserConversations(@PathVariable Long userId) {
        try {
            logger.info("Fetching conversations for user: {}", userId);
            List<String> conversations = conversationService.getConversationsByUserId(userId);
            if (conversations.isEmpty()) {
                return ResponseEntity.ok("No conversations found for user " + userId);
            }
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            logger.error("Error fetching conversations for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error fetching conversations: " + e.getMessage());
        }
    }
}
