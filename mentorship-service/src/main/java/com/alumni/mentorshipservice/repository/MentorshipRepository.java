package com.alumni.mentorshipservice.repository;

import com.alumni.mentorshipservice.domain.model.Mentorship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorshipRepository extends JpaRepository<Mentorship, Long> {
}