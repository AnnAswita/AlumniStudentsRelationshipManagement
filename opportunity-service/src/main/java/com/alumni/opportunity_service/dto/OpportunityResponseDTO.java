package com.alumni.opportunity_service.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OpportunityResponseDTO {

    private UUID opportunityId;
    private String title;
    private String description;
    private String type;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}

