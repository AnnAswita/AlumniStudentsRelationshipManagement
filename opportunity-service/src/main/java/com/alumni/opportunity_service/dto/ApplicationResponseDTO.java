package com.alumni.opportunity_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ApplicationResponseDTO {

    private UUID applicationId;
    private LocalDateTime appliedDate;
    private String status;

    private UUID studentId;
    private String studentName;

    private UUID opportunityId;
    private String opportunityTitle;
}
