package com.chatbotservices.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chatbotservices.model.Conversation;
import com.chatbotservices.model.User;
import com.chatbotservices.repository.ConversationRepository;
import com.chatbotservices.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private static final Logger logger = LoggerFactory.getLogger(ConversationService.class);

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    // Save user and bot messages in a single JSON-formatted column
    public Long saveUserAndBotConversationsInSingleColumn(Long userId, List<String> messages) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        // Format messages as JSON
        String messageJson = messages.stream()
            .map(msg -> (messages.indexOf(msg) % 2 == 0 ? "\"user\": \"" + msg + "\"" : "\"bot\": \"" + msg + "\""))
            .collect(Collectors.joining(", ", "{", "}"));

        Conversation conversation = new Conversation(messageJson, LocalDateTime.now(), user);
        conversationRepository.save(conversation);
        
        return conversation.getId();
    }

    // Retrieve conversations by user ID
    public List<String> getConversationsByUserId(Long userId) {
        logger.info("Retrieving conversations for user ID: {}", userId);
        return conversationRepository.findByUserId(userId)
            .stream()
            .map(Conversation::getMessages) // Extract JSON messages
            .collect(Collectors.toList());
    }
}
