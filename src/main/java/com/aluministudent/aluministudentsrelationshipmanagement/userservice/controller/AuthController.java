package com.aluministudent.aluministudentsrelationshipmanagement.userservice.controller;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        return service.login(email, password);
    }
}