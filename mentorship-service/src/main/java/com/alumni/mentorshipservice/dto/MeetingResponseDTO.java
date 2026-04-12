package com.alumni.mentorshipservice.dto;

import com.alumni.mentorshipservice.domain.valueobject.MentorshipStatus;
import lombok.Data;

@Data
public class MeetingResponseDTO {
    private Long id;
    private Long mentorshipId;
    private String description;
    private String date;
    private MentorshipStatus mentorshipStatus;
}
