package com.app.myntra.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders") // "order" is a reserved word in SQL
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;
    private String status;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
public Order() {
    }
    

    public Order(LocalDate orderDate, String status, double totalAmount, User user) {
        this.orderDate = orderDate;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    // Getters and Setters
}
