package com.chatbotservices.controller;

import com.chatbotservices.dto.RegisterRequest;
import com.chatbotservices.dto.UserDto;
import com.chatbotservices.model.User;
import com.chatbotservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = new User();
            user.setUserName(registerRequest.getUserName());
            user.setPassword(registerRequest.getPassword());
            user.setName(registerRequest.getName());
            user.setEmail(registerRequest.getEmail());
            user.setCity(registerRequest.getCity());
            user.setCountry(registerRequest.getCountry());
            user.setCourse(registerRequest.getCourse());
            user.setYear(registerRequest.getYear());
            user.setCollege(registerRequest.getCollege());

            User registeredUser = userService.registerUser(user, registerRequest.getSubscriptionName());
            return ResponseEntity.ok("User registered successfully with ID: " + registeredUser.getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getUserName(), loginRequest.getPassword());
            if (user != null) {
                return ResponseEntity.ok("Login successful. Welcome, " + user.getName() + "!");
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred during login.");
        }
    }

    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<?> getUserSubscriptionDetails(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(userDto.getSubscriptions());
    }
}