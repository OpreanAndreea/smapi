package com.coding_project.smapi.service;

import com.coding_project.smapi.dto.request.AuthRequest;
import com.coding_project.smapi.dto.request.RegisterRequest;
import com.coding_project.smapi.dto.response.AuthResponse;
import com.coding_project.smapi.entity.User;
import com.coding_project.smapi.enums.Role;
import com.coding_project.smapi.repository.UserRepository;
import com.coding_project.smapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user));
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername());
        return new AuthResponse(jwtUtil.generateToken(user));
    }
}
