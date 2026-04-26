package com.alumni.mentorshipservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MentorshipResponseDTO {
    private Long id;
    private Long studentId;
    private Long alumniId;
    private String status;
    private String studentName;
    private String alumniName;
}
