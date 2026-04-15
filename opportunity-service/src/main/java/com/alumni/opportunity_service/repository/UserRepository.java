package com.alumni.opportunity_service.repository;

import com.alumni.opportunity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
}

