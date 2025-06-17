package com.app.myntra.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String username;
private String email;

@JsonIgnore
private String password;

private String phoneNumber;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonManagedReference
private List<Cart> cartItems;

public User() {
}

public User(String username, String email, String password, String phoneNumber, List<Cart> cartItems) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.cartItems = cartItems;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

@Override
public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

@Override
@JsonIgnore
public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public String getPhoneNumber() {
    return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}

public List<Cart> getCartItems() {
    return cartItems;
}

public void setCartItems(List<Cart> cartItems) {
    this.cartItems = cartItems;
}

// UserDetails interface methods
@Override
@JsonIgnore
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList(); // Roles can be added here
}

@Override
@JsonIgnore
public boolean isAccountNonExpired() {
    return true;
}

@Override
@JsonIgnore
public boolean isAccountNonLocked() {
    return true;
}

@Override
@JsonIgnore
public boolean isCredentialsNonExpired() {
    return true;
}

@Override
@JsonIgnore
public boolean isEnabled() {
    return true;
}
}