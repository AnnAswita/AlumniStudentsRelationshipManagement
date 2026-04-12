package com.alumni.mentorshipservice.repository;

import com.alumni.mentorshipservice.domain.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    boolean existsByMentorshipIdAndDate(Long mentorshipId, String date);

    List<Meeting> findByMentorshipId(Long mentorshipId);
}
