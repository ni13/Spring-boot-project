package com.app.myntra.Entity;

import jakarta.persistence.*;
@Entity
@Table(name = "orders") 
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String status;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
public Order() {
    }
    

    public Order(String status, double totalAmount, User user) {

        this.status = status;
        this.totalAmount = totalAmount;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
