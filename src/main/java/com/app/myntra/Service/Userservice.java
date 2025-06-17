package com.app.myntra.Service;

import com.app.myntra.Entity.User;
import com.app.myntra.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Userservice {

@Autowired
private UserRepository userRepo;

public User createUser(User user) {
    return userRepo.save(user);
}

public User getUser(Long id) {
    return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
}

public List<User> getAllUsers() {
    return userRepo.findAll();
}

public User updateUser(Long id, User updatedUser) {
    User existing = getUser(id);
    existing.setUsername(updatedUser.getUsername());
    existing.setEmail(updatedUser.getEmail());
    existing.setPhoneNumber(updatedUser.getPhoneNumber());
    existing.setPassword(updatedUser.getPassword());
    return userRepo.save(existing);
}

public void deleteUser(Long id) {
    userRepo.deleteById(id);
}
}