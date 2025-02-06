package com.chatbotservices.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbotservices.model.Conversation;
import com.chatbotservices.model.User;
import com.chatbotservices.repository.ConversationRepository;
import com.chatbotservices.repository.UserRepository;

@Service
public class ConversationService {

    private static final Logger logger = LoggerFactory.getLogger(ConversationService.class);

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Conversation> saveUserAndBotConversations(Long userId, List<String> messages) {
        logger.info("Attempting to save user and chatbot conversations for user ID: {}", userId);
        
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            logger.warn("User ID {} not found.", userId);
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        List<Conversation> savedConversations = new ArrayList<>();

        for (String userMessage : messages) {
            Conversation userConversation = new Conversation(userMessage, LocalDateTime.now(), user);
            savedConversations.add(conversationRepository.save(userConversation));

            String botResponse = generateBotResponse(userMessage);
            Conversation botConversation = new Conversation(botResponse, LocalDateTime.now(), user);
            savedConversations.add(conversationRepository.save(botConversation));
        }

        logger.info("Successfully saved {} conversations (user and bot) for user ID: {}", savedConversations.size(), userId);
        return savedConversations;
    }

    public List<Conversation> getConversationsByUserId(Long userId) {
        logger.info("Retrieving conversations for user ID: {}", userId);
        List<Conversation> conversations = conversationRepository.findByUserId(userId);
        if (conversations.isEmpty()) {
            logger.warn("No conversations found for user ID: {}", userId);
        }
        return conversations;
    }

    private String generateBotResponse(String userMessage) {
        return "Bot: I'm here to help with your queries!"; // Placeholder logic for chatbot response
    }
}
