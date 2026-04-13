package com.alumni.opportunity_service.entity;

import com.alumni.opportunity_service.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;


    @Enumerated(EnumType.STRING)
    private UserRole role; // STUDENT or ALUMNI
}

