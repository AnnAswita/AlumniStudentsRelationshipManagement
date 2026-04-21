package com.alumni.mentorshipservice.repository;

import com.alumni.mentorshipservice.domain.model.Mentorship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentorshipRepository extends JpaRepository<Mentorship, Long> {
    Optional<Mentorship> findByStudentIdAndAlumniId(Long studentId, Long alumniId);
    List<Mentorship> findByStudentId(Long studentId);
    List<Mentorship> findByAlumniId(Long alumniId);
}