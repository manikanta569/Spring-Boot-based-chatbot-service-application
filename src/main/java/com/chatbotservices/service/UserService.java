package com.chatbotservices.service;

import com.chatbotservices.dto.UserDto;
import com.chatbotservices.dto.UserSubscriptionDto;
import com.chatbotservices.model.SubscriptionPlan;
import com.chatbotservices.model.User;
import com.chatbotservices.model.UserSubscription;
import com.chatbotservices.repository.SubscriptionPlanRepository;
import com.chatbotservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public User registerUser(User user, String subscriptionName) throws Exception {
        // Validate if username and email already exist
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new Exception("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email ID already exists");
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Find or create the subscription plan
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findByName(subscriptionName)
                .orElseThrow(() -> new Exception("Invalid subscription plan"));

        // Create a UserSubscription record
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUser(user);
        userSubscription.setSubscriptionPlan(subscriptionPlan);
        userSubscription.setSubscriptionType(subscriptionPlan.getName());
        userSubscription.setStartDate(LocalDate.now());
        userSubscription.setEndDate(LocalDate.now().plusDays(subscriptionPlan.getDurationInDays()));
        userSubscription.setStatus("Active");
        userSubscription.setConversationsUsed(0);

        user.setSubscriptions(List.of(userSubscription));

        return userRepository.save(user);
    }

    public User loginUser(String userName, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return convertToDto(user);
    }
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCountry(user.getCountry());
        userDto.setCity(user.getCity());
        userDto.setCourse(user.getCourse());
        userDto.setYear(user.getYear());
        userDto.setCollege(user.getCollege());

        userDto.setSubscriptions(user.getSubscriptions().stream()
                .map(this::convertToSubscriptionDto)
                .collect(Collectors.toList()));

        return userDto;
    }
    private UserSubscriptionDto convertToSubscriptionDto(UserSubscription subscription) {
        UserSubscriptionDto dto = new UserSubscriptionDto();
        dto.setId(subscription.getId());
        dto.setSubscriptionType(subscription.getSubscriptionType());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setStatus(subscription.getStatus());
        dto.setConversationsUsed(subscription.getConversationsUsed());
        dto.setUserName(subscription.getUser().getUserName()); // Add this line
        dto.setEmail(subscription.getUser().getEmail()); // Add email field

        return dto;
    }
}
