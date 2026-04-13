package com.aluministudent.aluministudentsrelationshipmanagement.userservice.controller;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.UserRequestDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.UserResponseDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO request) {
        return service.register(request);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable("id") Long id) {
        return service.getUser(id);
    }
}