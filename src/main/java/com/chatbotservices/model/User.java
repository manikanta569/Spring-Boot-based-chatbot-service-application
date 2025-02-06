package com.chatbotservices.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50) 
    private String userName;

    @Column(name = "password", nullable = false, length = 100) 
    private String password;

    @Column(name = "name", nullable = false, length = 100) 
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150) 
    private String email;

    @Column(name = "country", length = 100) 
    private String country;

    @Column(name = "city", length = 80) 
    private String city;

    @Column(name = "course", length = 100) 
    private String course;

    @Column(name = "year")
    private int year;

    @Column(name = "college", length = 100)
    private String college;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSubscription> subscriptions;
    
    
 // Inside User.java
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations;

    // Getters and Setters
    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public List<UserSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<UserSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}

