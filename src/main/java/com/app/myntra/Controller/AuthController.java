package com.app.myntra.Controller;

import com.app.myntra.model.*;
import com.app.myntra.Repository.UserRepository;
import com.app.myntra.Security.JwtUtil;
import com.app.myntra.Service.UserDetailsServiceImpl;
import com.app.myntra.Entity.User;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
private UserRepository userRepo;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword()
        )
    );

    User user = userRepo.findByUsername(request.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtUtil.generateToken(user);

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("userId", user.getId());

    return ResponseEntity.ok(response);
}
}