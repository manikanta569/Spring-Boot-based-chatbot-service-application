package com.chatbotservices.dto;

import java.math.BigDecimal;

public class SubscriptionPlanDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int durationInDays;
    private int conversationLimit;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public int getConversationLimit() {
        return conversationLimit;
    }

    public void setConversationLimit(int conversationLimit) {
        this.conversationLimit = conversationLimit;
    }
}

