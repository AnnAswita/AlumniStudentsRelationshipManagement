package com.aluministudent.aluministudentsrelationshipmanagement.userservice.dto;

import com.aluministudent.aluministudentsrelationshipmanagement.userservice.domain.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
}
