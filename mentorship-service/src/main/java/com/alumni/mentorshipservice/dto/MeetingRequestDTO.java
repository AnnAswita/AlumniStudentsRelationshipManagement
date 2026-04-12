package com.alumni.mentorshipservice.dto;
import lombok.Data;

@Data
public class MeetingRequestDTO {
    private Long mentorshipId;
    private String description;
    private String date;
}

