package com.alumni.opportunity_service.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ApplicationResponseDTO {

    private Long applicationId;
    private LocalDateTime appliedDate;
    private String status;

    private Long studentId;
    private String studentName;

    private Long opportunityId;
    private String opportunityTitle;
}
