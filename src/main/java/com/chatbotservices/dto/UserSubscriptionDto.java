package com.chatbotservices.dto;

import java.time.LocalDate;

public class UserSubscriptionDto {
    private Long id;
    private String subscriptionType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private int conversationsUsed;
    private String userName; // Add this field
    private String email; // New field


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConversationsUsed() {
        return conversationsUsed;
    }

    public void setConversationsUsed(int conversationsUsed) {
        this.conversationsUsed = conversationsUsed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

