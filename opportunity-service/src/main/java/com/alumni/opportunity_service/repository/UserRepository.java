package com.alumni.opportunity_service.repository;

import com.alumni.opportunity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

