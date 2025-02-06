package com.chatbotservices;

import com.chatbotservices.model.SubscriptionPlan;
import com.chatbotservices.repository.SubscriptionPlanRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataLoader {
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public DataLoader(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @PostConstruct
    public void loadData() {
        if (subscriptionPlanRepository.count() == 0) {
            subscriptionPlanRepository.save(new SubscriptionPlan(
                "Free", "Free plan", BigDecimal.ZERO, 1, 5
            ));
            subscriptionPlanRepository.save(new SubscriptionPlan(
                "Monthly", "Monthly subscription", new BigDecimal("9.99"), 30, 200
            ));
            subscriptionPlanRepository.save(new SubscriptionPlan(
                "Quarterly", "Quarterly subscription", new BigDecimal("24.99"), 180, 600
            ));
            subscriptionPlanRepository.save(new SubscriptionPlan(
                "Annual", "Annual subscription", new BigDecimal("49.99"), 365, 1000
            ));
            System.out.println("Subscription plans have been initialized.");
        } else {
            System.out.println("Subscription plans already exist in the database.");
        }
    }
}