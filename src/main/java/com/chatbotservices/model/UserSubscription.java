package com.chatbotservices.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usersubscription")
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subscriptionPlanId", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @Column(name = "subscriptionType", nullable = false, length = 50) // Changed length to 50
    private String subscriptionType;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;


    @Column(name = "status", nullable = false, length = 20) // Changed length to 20
    private String status;

    @Column(name = "conversationsUsed", nullable = false)
    private int conversationsUsed;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
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
}


