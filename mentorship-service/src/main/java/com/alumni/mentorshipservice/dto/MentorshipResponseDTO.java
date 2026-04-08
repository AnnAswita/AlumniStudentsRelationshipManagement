package com.alumni.mentorshipservice.dto;

import lombok.Data;

@Data
public class MentorshipResponseDTO {
    private Long id;
    private Long studentId;
    private Long alumniId;
    private String status;
}
