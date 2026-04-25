package com.aluministudent.aluministudentsrelationshipmanagement.userservice.service;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.config.JwtUtil;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.domain.Role;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.domain.User;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.LoginRequestDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.LoginResponseDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.UserRequestDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.UserResponseDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo, JwtUtil jwtUtil, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    // REGISTER USER
    public UserResponseDTO register(UserRequestDTO request) {
        User user = UserMapper.toEntity(request);

        // Encrypt password
        user.setPassword(encoder.encode(request.getPassword()));

        User saved = repo.save(user);
        return UserMapper.toDTO(saved);
    }

    // GET USER BY ID
    public UserResponseDTO getUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }

    // LOGIN USER
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password using BCrypt
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT with role
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new LoginResponseDTO(token,user.getId(),user.getRole().name());
    }

    public List<UserResponseDTO> getUsersByRole(String role) {
        Role enumRole = Role.valueOf(role.toUpperCase());
        return repo.findByRole(enumRole)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

}