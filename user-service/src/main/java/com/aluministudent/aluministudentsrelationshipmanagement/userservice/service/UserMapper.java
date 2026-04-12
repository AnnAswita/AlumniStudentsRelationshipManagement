package com.aluministudent.aluministudentsrelationshipmanagement.userservice.service;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.domain.User;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.UserRequestDTO;
import com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto.UserResponseDTO;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
