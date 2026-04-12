package com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.domain.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
