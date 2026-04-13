package com.aluministudent.aluministudentsrelationshipmanagement.userservice.controller;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.LoginRequestDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.LoginResponseDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return service.login(request);
    }
}